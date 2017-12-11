package io.fotoapparat.capability

import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.util.lineSeparator
import io.fotoapparat.util.wrap

/**
 * Capabilities of camera hardware.
 *
 * Sensor sensitivities is not guaranteed to always contain a value.
 */
data class Capabilities(
        val canZoom: Boolean,
        val flashModes: Set<Flash>,
        val focusModes: Set<FocusMode>,
        val canSmoothZoom: Boolean,
        val previewFpsRanges: Set<FpsRange>,
        val pictureResolutions: Set<Resolution>,
        val previewResolutions: Set<Resolution>,
        val sensorSensitivities: Set<Int>
) {

    init {
        flashModes.ensureNotEmpty()
        focusModes.ensureNotEmpty()
        previewFpsRanges.ensureNotEmpty()
        pictureResolutions.ensureNotEmpty()
        previewResolutions.ensureNotEmpty()
    }

    override fun toString(): String {
        return "Capabilities" + lineSeparator +
                "canZoom:" + canZoom.wrap() +
                "flashModes:" + flashModes.wrap() +
                "focusModes:" + focusModes.wrap() +
                "canSmoothZoom:" + canSmoothZoom.wrap() +
                "previewFpsRanges:" + previewFpsRanges.wrap() +
                "pictureResolutions:" + pictureResolutions.wrap() +
                "previewResolutions:" + previewResolutions.wrap() +
                "sensorSensitivities:" + sensorSensitivities.wrap()
    }
}

private inline fun <reified E> Set<E>.ensureNotEmpty() {
    if (isEmpty()) {
        throw IllegalArgumentException("Capabilities cannot have an empty Set<${E::class.java.simpleName}>.")
    }
}
