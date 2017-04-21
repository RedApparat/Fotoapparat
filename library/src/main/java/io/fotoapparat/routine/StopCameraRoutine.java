package io.fotoapparat.routine;

import io.fotoapparat.hardware.CameraDevice;

/**
 * Stops preview and closes the camera.
 */
public class StopCameraRoutine implements Runnable {

    private final CameraDevice cameraDevice;

    public StopCameraRoutine(CameraDevice cameraDevice) {
        this.cameraDevice = cameraDevice;
    }

    @Override
    public void run() {
        cameraDevice.stopPreview();
        cameraDevice.close();
    }

}
