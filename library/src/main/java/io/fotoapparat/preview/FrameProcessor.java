package io.fotoapparat.preview;

/**
 * Performs processing on preview frames.
 */
public interface FrameProcessor {

	/**
	 * Performs processing on preview frames.
	 *
	 * @param frame frame of the preview. Do not cache it as it will eventually be reused by the
	 *              camera.
	 */
	void processFrame(Frame frame);

}
