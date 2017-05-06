package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.CameraThread;

/**
 * Basic wrapper around the internal {@link CameraCaptureSession}
 * for a {@link CameraDevice} to provide the opened session synchronously.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class Session extends CameraCaptureSession.StateCallback {

    private final CountDownLatch sessionLatch = new CountDownLatch(1);
    private final CameraDevice camera;
    private final List<Surface> outputSurfaces;
    private final CameraThread cameraThread;
    private CameraCaptureSession session;

    Session(CameraDevice camera, List<Surface> outputSurfaces, CameraThread cameraThread) {
        this.camera = camera;
        this.outputSurfaces = outputSurfaces;
        this.cameraThread = cameraThread;
    }

    /**
     * Waits and returns the {@link CameraCaptureSession} synchronously after it has been
     * obtained.
     *
     * @return the requested {@link CameraCaptureSession} to open
     */
    public CameraCaptureSession getCaptureSession() {
        if (session == null) {
            createCaptureSession();
        }
        return session;
    }

    /**
     * Closes the {@link CameraCaptureSession} asynchronously.
     */
    public void close() {
        if (session != null) {
            session.close();
        }
    }

    @Override
    public void onConfigured(@NonNull CameraCaptureSession session) {
        this.session = session;
        sessionLatch.countDown();
    }

    @Override
    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
        session.close();
    }

    @Override
    public void onClosed(@NonNull CameraCaptureSession session) {
        super.onClosed(session);
    }

    private void createCaptureSession() {
        try {
            camera.createCaptureSession(
                    outputSurfaces,
                    this,
                    cameraThread.createHandler()
            );
            sessionLatch.await();
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        } catch (InterruptedException e) {
            // Do nothing
        }
    }
}
