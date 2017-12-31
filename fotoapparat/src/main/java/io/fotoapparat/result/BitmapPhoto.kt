package io.fotoapparat.result

import android.graphics.Bitmap

/**
 * Photo as [Bitmap].
 */
data class BitmapPhoto(
        /**
         * [Bitmap] of the photo.
         */
        @JvmField
        val bitmap: Bitmap,
        /**
         * Clockwise rotation relatively to screen orientation at the moment when photo was taken.
         */
        @JvmField
        val rotationDegrees: Int
)
