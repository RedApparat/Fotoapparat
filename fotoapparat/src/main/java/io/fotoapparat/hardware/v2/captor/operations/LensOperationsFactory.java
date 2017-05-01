package io.fotoapparat.hardware.v2.captor.operations;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.lens.FocusResultState;

/**
 * Factory which provides several lens operations.
 */
@SuppressWarnings("NewApi")
public class LensOperationsFactory {

    private final SessionManager sessionManager;
    private final CaptureRequestFactory captureRequestFactory;
    private final Handler handler = CameraThread
            .getInstance()
            .createHandler();

    public LensOperationsFactory(SessionManager sessionManager,
                                 CaptureRequestFactory captureRequestFactory) {
        this.sessionManager = sessionManager;
        this.captureRequestFactory = captureRequestFactory;
    }

    /**
     * @return A new operation to lock the lens focus.
     */
    public LensOperation<FocusResultState> createLockFocusOperation() {
        try {

            CameraCaptureSession session = sessionManager.getCaptureSession().getCaptureSession();
            CaptureRequest lockRequest = captureRequestFactory.createLockRequest();

            return new LockFocusOperation(session, lockRequest, handler);
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }

}
