@file:Suppress("DEPRECATION")

package io.fotoapparat.characteristic

import android.hardware.Camera

/**
 * Returns the [Characteristics] for the given `cameraId`.
 */
internal fun getCharacteristics(cameraId: Int): Characteristics {
    val info = Camera.CameraInfo()
    Camera.getCameraInfo(cameraId, info)
    return Characteristics(
            cameraId,
            info.facing.toLensPosition(),
            info.orientation
    )
}
