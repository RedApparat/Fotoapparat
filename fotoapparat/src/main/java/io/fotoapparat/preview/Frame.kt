package io.fotoapparat.preview

import io.fotoapparat.parameter.Resolution
import java.util.*

/**
 * Frame of the preview stream.
 */
data class Frame(
        /**
         * Resolution of the frame in pixels (before rotation).
         */
        val size: Resolution,
        /**
         * Image in NV21 format.
         */
        val image: ByteArray,
        /**
         * Clockwise rotation of the image in degrees relatively to user.
         */
        val rotation: Int
) {

    override fun toString(): String {
        return "Frame{" +
                "size=" + size +
                ", image= array(" + image.size + ")" +
                ", rotation=" + rotation +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Frame

        if (size != other.size) return false
        if (!Arrays.equals(image, other.image)) return false
        if (rotation != other.rotation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size.hashCode()
        result = 31 * result + Arrays.hashCode(image)
        result = 31 * result + rotation
        return result
    }

}
