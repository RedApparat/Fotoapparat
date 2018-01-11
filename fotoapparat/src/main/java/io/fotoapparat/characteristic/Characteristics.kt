package io.fotoapparat.characteristic

import io.fotoapparat.hardware.orientation.Orientation

/**
 * A set of information about the camera.
 */
internal data class Characteristics(
        val cameraId: Int,
        val lensPosition: LensPosition,
        val cameraOrientation: Orientation,
        val isMirrored: Boolean
)
