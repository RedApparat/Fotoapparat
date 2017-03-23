package io.fotoapparat.photo;

/**
 * Taken photo.
 */
public class Photo {

	/**
	 * Encoded image. Use {@link android.graphics.BitmapFactory#decodeByteArray(byte[], int, int)}
	 * to decode it.
	 */
	public final byte[] encodedImage;

	/**
	 * Clockwise rotation relatively to screen orientation at the moment when photo was taken.
	 */
	public final int rotationDegrees;

	public Photo(byte[] encodedImage,
				 int rotationDegrees) {
		this.encodedImage = encodedImage;
		this.rotationDegrees = rotationDegrees;
	}

}
