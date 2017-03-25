package io.fotoapparat.hardware.v2;

import android.hardware.camera2.CameraCaptureSession;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

/**
 * A wrapper around {@link CameraCaptureSession.StateCallback} to provide the opened
 * session synchronously.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class CaptureSessionCallback extends CameraCaptureSession.StateCallback {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private CameraCaptureSession session;

    @Override
    public void onConfigured(@NonNull CameraCaptureSession session) {
        this.session = session;
        countDownLatch.countDown();
    }

    @Override
    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
        session.close();
    }

    /**
     * Waits and returns the {@link CameraCaptureSession} synchronously after it has been
     * obtained.
     *
     * @return the requested {@link CameraCaptureSession} to open
     * @throws InterruptedException if the thread has been interrupted before
     *                              the session has been opened.
     */
    CameraCaptureSession getSession() throws InterruptedException {
        countDownLatch.await();
        return session;
    }
}
