package io.fotoapparat.result.transformer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.photo.Photo;

/**
 * Creates {@link BitmapPhoto} out of {@link Photo}.
 */
public class BitmapPhotoTransformer implements Transformer<Photo, BitmapPhoto> {

	@Override
	public BitmapPhoto transform(Photo input) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(
				input.encodedImage,
				0,
				input.encodedImage.length
		);

		return new BitmapPhoto(
				bitmap,
				input.rotationDegrees
		);
	}

}
