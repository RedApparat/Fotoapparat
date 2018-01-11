package io.fotoapparat.result.transformer

import io.fotoapparat.exception.FileSaveException
import io.fotoapparat.exif.ExifOrientationWriter
import io.fotoapparat.result.Photo
import java.io.*

/**
 * Saves [Photo] to file.
 */
internal class SaveToFileTransformer(
        private val file: File,
        private val exifOrientationWriter: ExifOrientationWriter
) : (Photo) -> Unit {

    override fun invoke(input: Photo) {
        val outputStream = try {
            FileOutputStream(file).buffered()
        } catch (e: FileNotFoundException) {
            throw FileSaveException(e)
        }

        try {
            saveImage(input, outputStream)

            exifOrientationWriter.writeExifOrientation(file, input.rotationDegrees)
        } catch (e: IOException) {
            throw FileSaveException(e)
        }
    }
}

@Throws(IOException::class)
private fun saveImage(input: Photo, outputStream: BufferedOutputStream) {
    outputStream.use {
        it.write(input.encodedImage)
        it.flush()
    }
}