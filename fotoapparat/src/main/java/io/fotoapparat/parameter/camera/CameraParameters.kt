package io.fotoapparat.parameter.camera

import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.util.lineSeparator
import io.fotoapparat.util.wrap


/**
 * Parameters of [CameraDevice].
 */
data class CameraParameters(
        val flashMode: Flash,
        val focusMode: FocusMode,
        val previewFpsRange: FpsRange,
        val sensorSensitivity: Int?,
        val pictureResolution: Resolution,
        val previewResolution: Resolution
) {
    override fun toString(): String {
        return "CameraParameters" + lineSeparator +
                "flashMode:" + flashMode.wrap() +
                "focusMode:" + focusMode.wrap() +
                "previewFpsRange:" + previewFpsRange.wrap() +
                "sensorSensitivity:" + sensorSensitivity?.wrap() +
                "pictureResolution:" + pictureResolution.wrap() +
                "previewResolution:" + previewResolution.wrap()
    }
}