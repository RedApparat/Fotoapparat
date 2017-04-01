package io.fotoapparat.request;

/**
 * An advanced request to take a photo.
 */
public class PhotoRequest {

	public PhotoRequest(PhotoRequestBuilder photoRequestBuilder) {

	}

	public static PhotoRequest empty() {
		return new PhotoRequest(null);
	}

}
