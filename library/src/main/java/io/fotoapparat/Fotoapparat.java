package io.fotoapparat;

import android.content.Context;

import io.fotoapparat.photo.PhotoResult;

/**
 * Camera. Takes pictures.
 */
public class Fotoapparat {

	Fotoapparat(FotoapparatBuilder builder) {
	}

	public static FotoapparatBuilder with(Context context) {
		return new FotoapparatBuilder();
	}

	public PhotoResult takePhoto() {
		return null;
	}

	public void start() {

	}

	public void stop() {

	}

}
