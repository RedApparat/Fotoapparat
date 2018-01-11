@file:Suppress("DEPRECATION")

package io.fotoapparat.parameter.camera.convert

import android.hardware.Camera
import io.fotoapparat.parameter.FpsRange

/**
 * Converts a [IntArray] into a [FpsRange].
 */
internal fun IntArray.toFpsRange(): FpsRange =
        FpsRange(
                this[Camera.Parameters.PREVIEW_FPS_MIN_INDEX],
                this[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]
        )
