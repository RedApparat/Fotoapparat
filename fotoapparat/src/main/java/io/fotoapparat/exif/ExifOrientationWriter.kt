package io.fotoapparat.exif

import java.io.File

/**
 * Writes Exif orientation attributes.
 */
internal interface ExifOrientationWriter {

    /**
     * Writes EXIF orientation tag into a file, overwriting it if it already exists.
     *
     * @param file     File of the image.
     * @param photo    Photo stored in the file.
     * @throws FileSaveException If writing has failed.
     */
    fun writeExifOrientation(file: File, rotationDegrees: Int)
}