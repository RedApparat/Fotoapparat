package io.fotoapparat;

import android.content.Context;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.photo.PhotoResult;
import io.fotoapparat.request.PhotoRequest;

/**
 * Camera. Takes pictures.
 */
public class Fotoapparat {

	Fotoapparat(FotoapparatBuilder builder) {
	}

	public static FotoapparatBuilder with(Context context) {
		return new FotoapparatBuilder();
	}

	public Capabilities getCapabilities() {
		return null;
	}

	public PhotoResult takePhoto() {
		return null;
	}

	public PhotoResult takePhoto(PhotoRequest photoRequest) {
		return null;
	}

	public void start() {

	}

	public void stop() {

	}
}
