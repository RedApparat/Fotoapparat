package io.fotoapparat.preview;

/**
 * Frame of the preview stream.
 */
public class Frame {

	/**
	 * Image in NV21 format.
	 */
	public final byte[] image;

	/**
	 * Clockwise rotation of the image in degrees relatively to user.
	 */
	public final int rotation;

	public Frame(byte[] image, int rotation) {
		this.image = image;
		this.rotation = rotation;
	}

}
