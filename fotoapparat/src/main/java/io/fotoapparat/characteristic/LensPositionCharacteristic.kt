@file:Suppress("DEPRECATION")

package io.fotoapparat.characteristic

import android.hardware.Camera
import io.fotoapparat.exception.camera.CameraException

/**
 * On some devices with double cameras v1 still returns external lens position (as in v2).
 */
private const val CAMERA_FACING_EXTERNAL = 2

/**
 * Maps between [LensPosition] and Camera v1 lens position code id.
 *
 * @receiver Camera facing info id.
 * @return [LensPosition] from the given lens position code id.
 * `null` if position code id is not supported.
 */
internal fun Int.toLensPosition(): LensPosition =
        when (this) {
            Camera.CameraInfo.CAMERA_FACING_FRONT -> LensPosition.Front
            Camera.CameraInfo.CAMERA_FACING_BACK -> LensPosition.Back
            CAMERA_FACING_EXTERNAL -> LensPosition.External
            else -> throw IllegalArgumentException("Lens position $this is not supported.")
        }

/**
 * Maps between [LensPosition] and Camera v1 code id.
 *
 * @receiver [LensPosition]
 * @return code of the camera as in [Camera.CameraInfo].
 */
fun LensPosition.toCameraId(): Int =
        (0 until Camera.getNumberOfCameras())
                .find { cameraId ->
                    this == getCharacteristics(cameraId).lensPosition
                }
                ?: throw CameraException("Device has no camera for the desired lens position(s).")
