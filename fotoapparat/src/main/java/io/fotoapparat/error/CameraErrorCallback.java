package io.fotoapparat.error;

import io.fotoapparat.hardware.CameraException;

/**
 * Notified when an camera error happens within Fotoapparat.
 * <p>
 * This method is always called from the main thread.
 */
public interface CameraErrorCallback {

    /**
     * No-op implementation of {@link CameraErrorCallback}.
     */
    CameraErrorCallback NULL = new CameraErrorCallback() {
        @Override
        public void onError(CameraException e) {
            // Do nothing
        }
    };

    /**
     * Notified when a camera error happens within Fotoapparat.
     */
    void onError(CameraException e);

}
