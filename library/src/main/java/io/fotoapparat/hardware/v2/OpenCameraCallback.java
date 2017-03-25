package io.fotoapparat.hardware.v2;

import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

/**
 * A wrapper around {@link CameraDevice.StateCallback} to provide the opened
 * camera synchronously.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class OpenCameraCallback extends CameraDevice.StateCallback {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private CameraDevice camera;

    @Override
    public void onOpened(@NonNull CameraDevice camera) {
        this.camera = camera;
    }

    @Override
    public void onDisconnected(@NonNull CameraDevice camera) {
        camera.close();
    }

    @Override
    public void onError(@NonNull CameraDevice camera, int error) {
        camera.close();
    }


    /**
     * Waits and returns the {@link CameraDevice} synchronously after it has been
     * obtained.
     *
     * @return the requested {@link CameraDevice} to open
     * @throws InterruptedException if the thread has been interrupted before
     *                              the camera has been obtained.
     */
    CameraDevice getCamera() throws InterruptedException {
        countDownLatch.await();
        return camera;
    }
}
