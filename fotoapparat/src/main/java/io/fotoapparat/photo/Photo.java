package io.fotoapparat.photo;

import java.util.Arrays;

/**
 * Taken photo.
 */
public class Photo {

    /**
     * Encoded image. Use {@link android.graphics.BitmapFactory#decodeByteArray(byte[], int, int)}
     * to decode it.
     */
    public final byte[] encodedImage;

    /**
     * Clockwise rotation relatively to screen orientation at the moment when photo was taken. To
     * display the photo in a correct orientation it needs to be rotated counter clockwise by this
     * value.
     */
    public final int rotationDegrees;

    public Photo(byte[] encodedImage,
                 int rotationDegrees) {
        this.encodedImage = encodedImage;
        this.rotationDegrees = rotationDegrees;
    }

    /**
     * @return empty {@link Photo}.
     */
    public static Photo empty() {
        return new Photo(new byte[0], 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return rotationDegrees == photo.rotationDegrees
                && Arrays.equals(encodedImage, photo.encodedImage);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(encodedImage);
        result = 31 * result + rotationDegrees;
        return result;
    }

}
