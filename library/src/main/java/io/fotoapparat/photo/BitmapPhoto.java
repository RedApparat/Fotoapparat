package io.fotoapparat.photo;

import android.graphics.Bitmap;

/**
 * Photo as {@link Bitmap}.
 */
public class BitmapPhoto {

    /**
     * {@link Bitmap} of the photo.
     */
    public final Bitmap bitmap;

    /**
     * Clockwise rotation relatively to screen orientation at the moment when photo was taken.
     */
    public final int rotationDegrees;

    public BitmapPhoto(Bitmap bitmap,
                       int rotationDegrees) {
        this.bitmap = bitmap;
        this.rotationDegrees = rotationDegrees;
    }
}
