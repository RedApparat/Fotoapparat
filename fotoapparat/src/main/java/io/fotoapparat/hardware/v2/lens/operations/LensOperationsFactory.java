package io.fotoapparat.hardware.v2.lens.operations;

import android.hardware.camera2.CameraAccessException;
import android.os.Handler;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.lens.operations.transformer.CaptureResultTransformer;
import io.fotoapparat.hardware.v2.lens.operations.transformer.ExposureResultTransformer;
import io.fotoapparat.hardware.v2.lens.operations.transformer.FocusResultTransformer;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.lens.CaptureResultState;
import io.fotoapparat.lens.ExposureResultState;
import io.fotoapparat.lens.FocusResult;

/**
 * Factory which provides several lens operations.
 */
@SuppressWarnings("NewApi")
public class LensOperationsFactory {

    private final SessionManager sessionManager;
    private final CaptureRequestFactory captureRequestFactory;
    private final Handler handler;

    public LensOperationsFactory(SessionManager sessionManager,
                                 CaptureRequestFactory captureRequestFactory,
                                 CameraThread cameraThread) {
        this.sessionManager = sessionManager;
        this.captureRequestFactory = captureRequestFactory;
        handler = cameraThread.createHandler();
    }

    /**
     * @return A new operation to lock the lens focus.
     */
    public LensOperation<FocusResult> createLockFocusOperation() {
        try {
            return LensOperation
                    .from(
                            captureRequestFactory.createLockRequest(),
                            handler,
                            new FocusResultTransformer(),
                            sessionManager.getCaptureSession()
                    );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }

    /**
     * @return A new operation to gather exposure data.
     */
    public LensOperation<ExposureResultState> createExposureGatheringOperation() {
        try {
            return LensOperation
                    .from(
                            captureRequestFactory.createExposureGatheringRequest(),
                            handler,
                            new ExposureResultTransformer(),
                            sessionManager.getCaptureSession()
                    );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }

    /**
     * @return A new operation to lock the lens focus.
     */
    public LensOperation<CaptureResultState> createCaptureOperation() {
        try {
            return LensOperation
                    .from(
                            captureRequestFactory.createCaptureRequest(),
                            handler,
                            new CaptureResultTransformer(),
                            sessionManager.getCaptureSession()
                    );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }
}
