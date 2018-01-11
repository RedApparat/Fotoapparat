package io.fotoapparat.result.transformer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.fotoapparat.exception.UnableToDecodeBitmapException
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.result.BitmapPhoto
import io.fotoapparat.result.Photo

/**
 * Creates [BitmapPhoto] out of [Photo].
 */
internal class BitmapPhotoTransformer(
        private val sizeTransformer: ResolutionTransformer
) : (Photo) -> BitmapPhoto {

    override fun invoke(input: Photo): BitmapPhoto {
        val originalResolution = input.readResolution()
        val desiredResolution = sizeTransformer(originalResolution)

        val scaleFactor = computeScaleFactor(
                originalResolution = originalResolution,
                desiredResolution = desiredResolution
        )

        val decodedBitmap = input.decodeBitmap(scaleFactor) ?: throw UnableToDecodeBitmapException()

        val bitmap = if (decodedBitmap.width == desiredResolution.width && decodedBitmap.height == desiredResolution.height) {
            decodedBitmap
        } else {
            Bitmap.createScaledBitmap(
                    decodedBitmap,
                    desiredResolution.width,
                    desiredResolution.height,
                    true
            )
        }

        return BitmapPhoto(
                bitmap,
                input.rotationDegrees
        )
    }

}

private fun Photo.decodeBitmap(scaleFactor: Float): Bitmap? {
    val options = BitmapFactory.Options()
    options.inSampleSize = scaleFactor.toInt()

    return BitmapFactory.decodeByteArray(
            encodedImage,
            0,
            encodedImage.size
    )
}

private fun Photo.readResolution(): Resolution {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true

    BitmapFactory.decodeByteArray(
            encodedImage,
            0,
            encodedImage.size,
            options
    )

    return Resolution(
            options.outWidth,
            options.outHeight
    )
}

private fun computeScaleFactor(
        originalResolution: Resolution,
        desiredResolution: Resolution
): Float = Math.min(
        originalResolution.width / desiredResolution.width.toFloat(),
        originalResolution.height / desiredResolution.height.toFloat()
)