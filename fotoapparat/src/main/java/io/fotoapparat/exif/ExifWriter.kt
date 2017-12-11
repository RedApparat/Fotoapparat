package io.fotoapparat.exif

import android.media.ExifInterface
import io.fotoapparat.exception.FileSaveException
import io.fotoapparat.result.Photo
import java.io.File
import java.io.IOException

/**
 * Writes Exif attributes.
 */
internal object ExifWriter : ExifOrientationWriter {


    @Throws(FileSaveException::class)
    override fun writeExifOrientation(file: File, photo: Photo) {
        try {
            val exifInterface = ExifInterface(file.path)
            exifInterface.setAttribute(
                    ExifInterface.TAG_ORIENTATION,
                    toExifOrientation(photo.rotationDegrees).toString()
            )
            exifInterface.saveAttributes()
        } catch (e: IOException) {
            throw FileSaveException(e)
        }
    }

    private fun toExifOrientation(rotationDegrees: Int): Int {
        val compensationRotationDegrees = (360 - rotationDegrees) % 360

        return when (compensationRotationDegrees) {
            90 -> ExifInterface.ORIENTATION_ROTATE_90
            180 -> ExifInterface.ORIENTATION_ROTATE_180
            270 -> ExifInterface.ORIENTATION_ROTATE_270
            else -> ExifInterface.ORIENTATION_NORMAL
        }
    }
}

