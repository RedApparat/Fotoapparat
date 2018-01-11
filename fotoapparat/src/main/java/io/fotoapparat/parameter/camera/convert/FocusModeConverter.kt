@file:Suppress("DEPRECATION")

package io.fotoapparat.parameter.camera.convert

import android.hardware.Camera
import io.fotoapparat.parameter.FocusMode


/**
 * Maps between [FocusMode] and Camera v1 focus codes.
 *
 * @receiver Code of focus mode as in [Camera.Parameters].
 * @return [FocusMode] from given camera code. `null` if camera code is not supported.
 */
internal fun String.toFocusMode(): FocusMode? =
        when (this) {
            Camera.Parameters.FOCUS_MODE_EDOF -> FocusMode.Edof
            Camera.Parameters.FOCUS_MODE_AUTO -> FocusMode.Auto
            Camera.Parameters.FOCUS_MODE_MACRO -> FocusMode.Macro
            Camera.Parameters.FOCUS_MODE_FIXED -> FocusMode.Fixed
            Camera.Parameters.FOCUS_MODE_INFINITY -> FocusMode.Infinity
            Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO -> FocusMode.ContinuousFocusVideo
            Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE -> FocusMode.ContinuousFocusPicture
            else -> null
        }

/**
 * Maps between [FocusMode] and Camera v1 focus codes.
 *
 * @receiver FocusMode mode.
 * @return code of the focus mode as in [Camera.Parameters].
 */
internal fun FocusMode.toCode(): String =
        when (this) {
            FocusMode.Edof -> Camera.Parameters.FOCUS_MODE_EDOF
            FocusMode.Auto -> Camera.Parameters.FOCUS_MODE_AUTO
            FocusMode.Macro -> Camera.Parameters.FOCUS_MODE_MACRO
            FocusMode.Fixed -> Camera.Parameters.FOCUS_MODE_FIXED
            FocusMode.Infinity -> Camera.Parameters.FOCUS_MODE_INFINITY
            FocusMode.ContinuousFocusVideo -> Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
            FocusMode.ContinuousFocusPicture -> Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
        }