package io.fotoapparat.exif

import androidx.exifinterface.media.ExifInterface
import io.fotoapparat.exception.FileSaveException
import java.io.File
import java.io.IOException

/**
 * Writes Exif attributes.
 */
internal object ExifWriter : ExifOrientationWriter {

    @Throws(FileSaveException::class)
    override fun writeExifOrientation(file: File, rotationDegrees: Int) {
        try {
            ExifInterface(file.path).apply {
                setAttribute(
                        ExifInterface.TAG_ORIENTATION,
                        toExifOrientation(rotationDegrees).toString()
                )
                saveAttributes()
            }
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

