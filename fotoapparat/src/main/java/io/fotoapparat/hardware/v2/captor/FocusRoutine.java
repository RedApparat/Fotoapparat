package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.AutoFocusOperator;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.session.Session;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.result.FocusResultState;

/**
 * Performs a lens focus routine.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FocusRoutine implements AutoFocusOperator {
    private final SessionManager sessionManager;
    private final CaptureRequestFactory captureRequestFactory;

    public FocusRoutine(CaptureRequestFactory captureRequestFactory,
                        SessionManager sessionManager) {
        this.captureRequestFactory = captureRequestFactory;
        this.sessionManager = sessionManager;
    }

    @Override
    public FocusResultState autoFocus() {
        try {
            focusLens();
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
        return null; // TODO: 01.05.17
    }

    private void focusLens() throws CameraAccessException {
        Session session = sessionManager.getCaptureSession();

        Stage stage = Stage.UNFOCUSED;
        while (stage == Stage.UNFOCUSED) {
            stage = triggerAutoFocus(session);
        }
    }

    private Stage triggerAutoFocus(Session session) throws CameraAccessException {

        StageCallback stageCallback = new LockFocusCallback();

        session.getCaptureSession()
                .capture(
                        captureRequestFactory.createLockRequest(),
                        stageCallback,
                        CameraThread
                                .getInstance()
                                .createHandler()
                );

        return stageCallback.getCaptureStage();
    }

}
