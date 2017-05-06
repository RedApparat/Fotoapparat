package io.fotoapparat.hardware.v2.connection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.CameraThread;

/**
 * Returns the open {@link CameraDevice}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@SuppressWarnings("MissingPermission")
class OpenCameraTask extends CameraDevice.StateCallback {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final CameraManager manager;
    private final CameraThread cameraThread;
    private CameraDevice camera;

    OpenCameraTask(CameraManager manager, CameraThread cameraThread) {
        this.manager = manager;
        this.cameraThread = cameraThread;
    }

    @NonNull
    public CameraDevice execute(String cameraId) {
        try {
            manager.openCamera(
                    cameraId,
                    this,
                    cameraThread.createHandler()
            );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            // do nothing
        }

        return camera;
    }

    @Override
    public void onOpened(@NonNull CameraDevice camera) {
        this.camera = camera;
        countDownLatch.countDown();
    }

    @Override
    public void onDisconnected(@NonNull CameraDevice camera) {
        camera.close();
    }

    @Override
    public void onError(@NonNull CameraDevice camera, int error) {
        camera.close();
    }
}
