package io.fotoapparat.result;

import java.util.concurrent.Future;

import io.fotoapparat.photo.Photo;

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
