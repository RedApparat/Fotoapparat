package io.fotoapparat.hardware.v2.session;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.Collections;
import java.util.List;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.v2.CameraThread;

/**
 * Wrapper around the internal {@link android.hardware.camera2.CameraCaptureSession}
 * for a particular {@link CameraDevice}.
 * <p>
 * It facilitates a {@link SurfaceTexture} to preview the results of the {@link CameraDevice}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class PreviewSession extends Session implements PreviewOperator {

    private final CaptureRequest captureRequest;
    private CameraCaptureSession captureSession;

    PreviewSession(CameraDevice camera,
                   CaptureRequest captureRequest,
                   List<Surface> surfaces,
                   CameraThread cameraThread) {
        super(camera, Collections.unmodifiableList(surfaces), cameraThread);
        this.captureRequest = captureRequest;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startPreview() {
        try {
            captureSession = getCaptureSession();
            captureSession.setRepeatingRequest(
                    captureRequest,
                    null,
                    null
            );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }

    @Override
    public void stopPreview() {
        if (captureSession == null) {
            throw new IllegalStateException(
                    "Tried to stop preview, but previews has not been yet started."
            );
        }
        captureSession.close();
    }
}
