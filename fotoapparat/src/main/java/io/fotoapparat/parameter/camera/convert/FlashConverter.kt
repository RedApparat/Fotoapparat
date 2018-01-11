@file:Suppress("DEPRECATION")

package io.fotoapparat.parameter.camera.convert

import android.hardware.Camera
import io.fotoapparat.parameter.Flash

/**
 * Maps between [Flash] and Camera v1 flash codes.
 *
 * @receiver Code of flash mode as in [Camera.Parameters].
 * @return [Flash] from given camera code. `null` if camera code is not supported.
 */
internal fun String.toFlash(): Flash? =
        when (this) {
            Camera.Parameters.FLASH_MODE_ON -> Flash.On
            Camera.Parameters.FLASH_MODE_OFF -> Flash.Off
            Camera.Parameters.FLASH_MODE_AUTO -> Flash.Auto
            Camera.Parameters.FLASH_MODE_TORCH -> Flash.Torch
            Camera.Parameters.FLASH_MODE_RED_EYE -> Flash.AutoRedEye
            else -> null
        }

/**
 * Maps between [Flash] and Camera v1 flash codes.
 *
 * @receiver Flash mode.
 * @return code of the flash mode as in [Camera.Parameters].
 */
internal fun Flash.toCode(): String =
        when (this) {
            Flash.On -> Camera.Parameters.FLASH_MODE_ON
            Flash.Off -> Camera.Parameters.FLASH_MODE_OFF
            Flash.Auto -> Camera.Parameters.FLASH_MODE_AUTO
            Flash.Torch -> Camera.Parameters.FLASH_MODE_TORCH
            Flash.AutoRedEye -> Camera.Parameters.FLASH_MODE_RED_EYE
        }