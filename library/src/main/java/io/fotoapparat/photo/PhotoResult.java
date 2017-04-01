package io.fotoapparat.photo;

import java.util.concurrent.Future;

/**
 * Result of taking the photo.
 */
public class PhotoResult {

	private PhotoResult(Future<Photo> photoFuture) {

	}

	public static PhotoResult fromFuture(Future<Photo> photoFuture) {
		return new PhotoResult(photoFuture);
	}

}
