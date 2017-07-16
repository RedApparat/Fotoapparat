package io.fotoapparat.preview;

import android.support.annotation.NonNull;

/**
 * Stream of preview frames from the camera.
 */
public interface PreviewStream {

    /**
     * Null-object for {@link PreviewStream}.
     */
    PreviewStream NULL = new PreviewStream() {
        @Override
        public void addFrameToBuffer() {
            // Do nothing
        }

        @Override
        public void addProcessor(@NonNull FrameProcessor processor) {
            // Do nothing
        }

        @Override
        public void removeProcessor(@NonNull FrameProcessor processor) {
            // Do nothing
        }

        @Override
        public void start() {
            // Do nothing
        }
    };

    /**
     * Adds new frame to buffer.
     */
    void addFrameToBuffer();

    /**
     * Registers new processor. If processor was already added before, does nothing.
     */
    void addProcessor(@NonNull FrameProcessor processor);

    /**
     * Unregisters the processor. If processor was not registered before, does nothing.
     */
    void removeProcessor(@NonNull FrameProcessor processor);

    /**
     * Starts preview stream. After preview is started frame processors will start receiving frames.
     */
    void start();

}
