package io.fotoapparat.result

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import io.fotoapparat.exception.FileSaveException
import io.fotoapparat.exif.ExifWriter
import io.fotoapparat.log.Logger
import io.fotoapparat.result.transformer.*
import io.fotoapparat.result.transformer.BitmapPhotoTransformer
import io.fotoapparat.result.transformer.SaveToFileTransformer
import io.fotoapparat.result.transformer.SaveToImagesMediaStoreTransformer
import java.io.File
import java.util.concurrent.Future

/**
 * Result of taking the photo.
 */
class PhotoResult internal constructor(private val pendingResult: PendingResult<Photo>) {

    /**
     * Converts result to [Bitmap] of size provided by the [sizeTransformer].
     *
     * @param sizeTransformer Given the original size of the photo, returns the updated size so that
     * photo will be downscaled, upscaled or unchanged.
     * @return result as pending [BitmapPhoto] which will be available at some point in the
     * future.
     */
    @JvmOverloads
    fun toBitmap(
            sizeTransformer: ResolutionTransformer = originalResolution()
    ): PendingResult<BitmapPhoto> =
            pendingResult.transform(BitmapPhotoTransformer(sizeTransformer))

    /**
     * Saves result to file.
     *
     * @return pending operation which completes when photo is saved to file.
     * @throws FileSaveException If the file cannot be saved.
     */
    fun saveToFile(file: File): PendingResult<Unit> =
            pendingResult.transform(SaveToFileTransformer(
                    file = file,
                    exifOrientationWriter = ExifWriter
            ))

    /**
     * Saves result to the images media store.
     *
     * @param contentResolver needed for interacting with the MediaStore.
     * @param contentValues bundle of info associated with the image that will be saved.
     * @return pending operation which completes when photo is saved to the images media store.
     * @throws FileSaveException If the file cannot be saved.
     */
    fun saveToImagesMediaStore(
            contentValues: ContentValues,
            contentResolver: ContentResolver
    ): PendingResult<Unit> =
            pendingResult.transform(SaveToImagesMediaStoreTransformer(
                    contentValues = contentValues,
                    contentResolver = contentResolver
            ))

    /**
     * @return result as [PendingResult].
     */
    fun toPendingResult(): PendingResult<Photo> = pendingResult

    companion object {

        /**
         * Creates a new instance of advanced result from a Future result.
         *
         * @param photoFuture The future result of a [Photo].
         * @return The result.
         */
        internal fun fromFuture(
                photoFuture: Future<Photo>,
                logger: Logger
        ) = PhotoResult(
                PendingResult.fromFuture(photoFuture, logger)
        )

    }

}