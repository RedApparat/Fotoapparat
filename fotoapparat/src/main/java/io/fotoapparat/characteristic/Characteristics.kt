package io.fotoapparat.characteristic

/**
 * A set of information about the camera.
 */
internal data class Characteristics(
        val cameraId: Int,
        val lensPosition: LensPosition,
        val orientation: Int,
        val isMirrored: Boolean
)
