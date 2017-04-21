package io.fotoapparat.routine;

import io.fotoapparat.hardware.CameraDevice;

/**
 * Performs auto focus.
 */
public class AutoFocusRoutine implements Runnable {

    private final CameraDevice cameraDevice;

    public AutoFocusRoutine(CameraDevice cameraDevice) {
        this.cameraDevice = cameraDevice;
    }

    @Override
    public void run() {
        cameraDevice.autoFocus();
    }

}
