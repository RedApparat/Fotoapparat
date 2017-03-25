package io.fotoapparat.hardware.v2;

import io.fotoapparat.photo.Photo;

/**
 * Takes a picture.
 */
public interface PhotoCaptor {

	/**
	 * Takes a picture.
	 */
	Photo takePicture();
}
