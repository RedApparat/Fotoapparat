package io.fotoapparat.error;

/**
 * Notified when an unrecoverable error happens within Fotoapparat. Typically that means that camera
 * can't be started and user should be notified about that.
 * <p>
 * This method is always called from the main thread.
 */
public interface UnrecoverableErrorCallback {

    /**
     * No-op implementation of {@link UnrecoverableErrorCallback}.
     */
    UnrecoverableErrorCallback NULL = new UnrecoverableErrorCallback() {
        @Override
        public void onError(UnrecoverableErrorReport report) {
            // Do nothing
        }
    };

    /**
     * Notified when an unrecoverable error happens within Fotoapparat.
     *
     * @param report contains information about the error.
     */
    void onError(UnrecoverableErrorReport report);

}
