package io.fotoapparat.result.transformer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import io.fotoapparat.parameter.Size;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.photo.Photo;
import io.fotoapparat.result.RecoverableRuntimeException;

/**
 * Creates {@link BitmapPhoto} out of {@link Photo}.
 */
public class BitmapPhotoTransformer implements Transformer<Photo, BitmapPhoto> {

    private final Transformer<Size, Size> sizeTransformer;

    public BitmapPhotoTransformer(Transformer<Size, Size> sizeTransformer) {
        this.sizeTransformer = sizeTransformer;
    }

    @Override
    public BitmapPhoto transform(Photo input) {
        Size originalSize = readImageSize(input);
        Size desiredSize = sizeTransformer.transform(originalSize);

        float scaleFactor = computeScaleFactor(originalSize, desiredSize);

        Bitmap bitmap = readImage(input, scaleFactor);

        ensureBitmapDecoded(bitmap);

        if (bitmap.getWidth() != desiredSize.width || bitmap.getHeight() != desiredSize.height) {
            bitmap = Bitmap.createScaledBitmap(bitmap, desiredSize.width, desiredSize.height, true);
        }

        return new BitmapPhoto(
                bitmap,
                input.rotationDegrees
        );
    }

    private void ensureBitmapDecoded(Bitmap bitmap) {
        if (bitmap == null) {
            throw new UnableToDecodeBitmapException();
        }
    }

    private Bitmap readImage(Photo image, float scaleFactor) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = (int) scaleFactor;

        return BitmapFactory.decodeByteArray(
                image.encodedImage,
                0,
                image.encodedImage.length
        );
    }

    private float computeScaleFactor(Size originalSize, Size desiredSize) {
        return Math.min(
                originalSize.width / (float) desiredSize.width,
                originalSize.height / (float) desiredSize.height
        );
    }

    private Size readImageSize(Photo image) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeByteArray(
                image.encodedImage,
                0,
                image.encodedImage.length,
                options
        );

        return new Size(
                options.outWidth,
                options.outHeight
        );
    }

    /**
     * Thrown when it is not possible to decode bitmap from byte array.
     */
    private static class UnableToDecodeBitmapException extends RecoverableRuntimeException {

        public UnableToDecodeBitmapException() {
            super("Unable to decode bitmap");
        }

    }

}
