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

	@Override
	public boolean equals(Object obj) {
		return obj instanceof PhotoRequest;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
