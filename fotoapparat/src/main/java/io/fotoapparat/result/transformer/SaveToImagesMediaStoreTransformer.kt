package io.fotoapparat.result.transformer

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Build
import android.provider.MediaStore
import io.fotoapparat.exception.FileSaveException
import io.fotoapparat.result.Photo
import java.io.*
import java.lang.Exception

/**
 * Saves [Photo] to the images media store.
 */
internal class SaveToImagesMediaStoreTransformer(
        private val contentValues: ContentValues,
        private val contentResolver: ContentResolver
) : (Photo) -> Unit {

    override fun invoke(input: Photo) {
        val imageGallery = getImageGalleryUri()

        // Marks the image as pending so that just the owner has access to it during the saving process.
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 1)

        val imageUri = try {
            contentResolver.insert(imageGallery, contentValues)!!
        } catch (e: Exception) {
            throw FileSaveException(e)
        }

        val outputStream = try {
            contentResolver.openOutputStream(imageUri)!!.buffered()
        } catch (e: FileNotFoundException) {
            throw FileSaveException(e)
        }

        try {
            saveImage(input, outputStream)
        } catch (e: IOException) {
            throw FileSaveException(e)
        }

        // Releases the file.
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        contentResolver.update(imageUri, contentValues, null, null)
    }

    private fun getImageGalleryUri() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ->
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        else ->
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    }
}

@Throws(IOException::class)
private fun saveImage(input: Photo, outputStream: BufferedOutputStream) {
    outputStream.use {
        it.write(input.encodedImage)
        it.flush()
    }
}