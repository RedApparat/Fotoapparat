@file:Suppress("DEPRECATION")

package io.fotoapparat.parameter.camera.convert

import android.hardware.Camera
import io.fotoapparat.parameter.AntiBandingMode

/**
 * Converts an anti banding mode code to a [AntiBandingMode].
 *
 * @receiver Code of the anti banding mode as in [Camera.Parameters].
 * @return The [io.fotoapparat.Fotoapparat]'s camera [AntiBandingMode]. `null` if camera code is not supported.
 */
fun String.toAntiBandingMode(): AntiBandingMode? =
        when (this) {
            Camera.Parameters.ANTIBANDING_AUTO -> AntiBandingMode.Auto
            Camera.Parameters.ANTIBANDING_50HZ -> AntiBandingMode.HZ50
            Camera.Parameters.ANTIBANDING_60HZ -> AntiBandingMode.HZ60
            Camera.Parameters.ANTIBANDING_OFF -> AntiBandingMode.None
            else -> null
        }

/**
 * Converts a [AntiBandingMode] to a antiBandingMode mode code as in [Camera.Parameters].
 *
 * @receiver The [io.fotoapparat.Fotoapparat]'s camera [AntiBandingMode].
 * @return anti banding mode code as in [Camera.Parameters].
 */
fun AntiBandingMode.toCode(): String =
        when (this) {
            AntiBandingMode.Auto -> Camera.Parameters.ANTIBANDING_AUTO
            AntiBandingMode.HZ50 -> Camera.Parameters.ANTIBANDING_50HZ
            AntiBandingMode.HZ60 -> Camera.Parameters.ANTIBANDING_60HZ
            AntiBandingMode.None -> Camera.Parameters.ANTIBANDING_OFF
        }