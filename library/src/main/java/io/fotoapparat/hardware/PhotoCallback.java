package io.fotoapparat.hardware;

import io.fotoapparat.photo.Photo;

/**
 * Callback which is called when photo is taken.
 */
public interface PhotoCallback {

	/**
	 * Callback which is called when photo is taken.
	 */
	void onPhotoTaken(Photo photo);

}
