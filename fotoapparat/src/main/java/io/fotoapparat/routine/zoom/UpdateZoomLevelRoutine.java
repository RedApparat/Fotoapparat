package io.fotoapparat.routine.zoom;

import android.support.annotation.FloatRange;

import io.fotoapparat.hardware.CameraDevice;

/**
 * Updates zoom level of the camera. If zoom is not supported - does nothing.
 */
public class UpdateZoomLevelRoutine {

    private final CameraDevice cameraDevice;

    public UpdateZoomLevelRoutine(CameraDevice cameraDevice) {
        this.cameraDevice = cameraDevice;
    }

    /**
     * Updates zoom level of the camera. If zoom is not supported - does nothing.
     *
     * @param zoomLevel zoom level of the camera. Value between 0 and 1.
     */
    public void updateZoomLevel(@FloatRange(from = 0f, to = 1f) float zoomLevel) {
        ensureInBounds(zoomLevel);

        if (cameraDevice.getCapabilities().isZoomSupported()) {
            cameraDevice.setZoom(zoomLevel);
        }
    }

    private void ensureInBounds(float zoomLevel) {
        if (zoomLevel < 0f || zoomLevel > 1f) {
            throw new LevelOutOfRangeException(zoomLevel);
        }
    }

    /**
     * Thrown when zoom level is outside of [0..1] range.
     */
    static class LevelOutOfRangeException extends RuntimeException {

        public LevelOutOfRangeException(float zoomLevel) {
            super(zoomLevel + " is out of range [0..1]");
        }

    }

}
