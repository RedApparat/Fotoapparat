package io.fotoapparat.hardware.v2.stream;

/**
 * Observer which accepts a {@link OnFrameAcquiredListener}.
 */
public interface OnImageAcquiredObserver {

    /**
     * Sets a {@link OnFrameAcquiredListener}.
     *
     * @param listener The listener to be used.
     */
    void setListener(OnFrameAcquiredListener listener);

    /**
     * Notified when an image has been acquired.
     */
    interface OnFrameAcquiredListener {

        /**
         * Called when an image has been acquired.
         *
         * @param bytes The image as a byte array.
         */
        void onFrameAcquired(byte[] bytes);

    }
}

