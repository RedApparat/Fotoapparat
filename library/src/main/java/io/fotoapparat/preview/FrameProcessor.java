package io.fotoapparat.preview;

/**
 * Performs processing on preview frames.
 * <p>
 * Frame processors are called from worker thread (aka non-UI thread). After
 * {@link #processFrame(Frame)} completes the frame is returned back to the pool where it is reused
 * afterwards. This means that implementations should take special care to not do any operations on
 * frame after method completes.
 */
public interface FrameProcessor {

    /**
     * Performs processing on preview frames. Read class description for more details.
     *
     * @param frame frame of the preview. Do not cache it as it will eventually be reused by the
     *              camera.
     */
    void processFrame(Frame frame);

}
