package io.fotoapparat.result

import android.graphics.BitmapFactory
import java.util.*

/**
 * Taken photo.
 */
data class Photo(

        /**
         * Encoded image. Use [android.graphics.BitmapFactory.decodeByteArray]
         * to decode it.
         */
        @JvmField
        val encodedImage: ByteArray,

        /**
         * Clockwise rotation relatively to screen orientation at the moment when photo was taken. To
         * display the photo in a correct orientation it needs to be rotated counter clockwise by this
         * value.
         */
        @JvmField
        val rotationDegrees: Int
) {

    /**
     * The height of the photo.
     */
    val height by lazy { decodedBounds.height }

    /**
     * The width of the photo.
     */
    val width by lazy { decodedBounds.width }

    private val decodedBounds by lazy {
        BitmapFactory.decodeByteArray(
                encodedImage,
                0,
                encodedImage.size,
                BitmapFactory.Options().apply {
                            inJustDecodeBounds = true
                        }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        if (!Arrays.equals(encodedImage, other.encodedImage)) return false
        if (rotationDegrees != other.rotationDegrees) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(encodedImage)
        result = 31 * result + rotationDegrees
        return result
    }

    companion object {

        private val EMPTY by lazy { Photo(encodedImage = ByteArray(0), rotationDegrees = 0) }

        /**
         * @return empty [Photo].
         */
        internal fun empty(): Photo = EMPTY
    }

}