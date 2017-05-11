package io.fotoapparat.util;

import android.media.ExifInterface;

import java.io.File;
import java.io.IOException;

import io.fotoapparat.photo.Photo;
import io.fotoapparat.result.transformer.SaveToFileTransformer;

/**
 * Writes EXIF orientation tag into a file.
 */
public class ExifOrientationWriter {

    /**
     * Writes EXIF orientation tag into a file, overwriting it if it already exists.
     *
     * @param file     File of the image.
     * @param photo    Photo stored in the file.
     * @throws IOException If writing has failed.
     */
    public void writeExifOrientation(File file, Photo photo) throws IOException {
        try {
            ExifInterface exifInterface = new ExifInterface(file.getPath());
            exifInterface.setAttribute(
                    ExifInterface.TAG_ORIENTATION,
                    toExifOrientation(photo.rotationDegrees)
            );
            exifInterface.saveAttributes();
        } catch (IOException e) {
            throw new SaveToFileTransformer.FileSaveException(e);
        }
    }

    private String toExifOrientation(int rotationDegrees) {
        final int compensationRotationDegrees = (360 - rotationDegrees) % 360;

        switch (compensationRotationDegrees) {
            case 90:
                return String.valueOf(ExifInterface.ORIENTATION_ROTATE_90);
            case 180:
                return String.valueOf(ExifInterface.ORIENTATION_ROTATE_180);
            case 270:
                return String.valueOf(ExifInterface.ORIENTATION_ROTATE_270);
            default:
                return String.valueOf(ExifInterface.ORIENTATION_NORMAL);
        }
    }

}
