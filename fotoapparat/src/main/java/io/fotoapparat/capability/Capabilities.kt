package io.fotoapparat.capability

import io.fotoapparat.parameter.*
import io.fotoapparat.util.lineSeparator
import io.fotoapparat.util.wrap

/**
 * Capabilities of camera hardware.
 *
 * Sensor sensitivities is not guaranteed to always contain a value.
 */
data class Capabilities(
        val zoom: Zoom,
        val flashModes: Set<Flash>,
        val focusModes: Set<FocusMode>,
        val canSmoothZoom: Boolean,
        val maxFocusAreas: Int,
        val maxMeteringAreas: Int,
        val jpegQualityRange: IntRange,
        val exposureCompensationRange: IntRange,
        val previewFpsRanges: Set<FpsRange>,
        val antiBandingModes: Set<AntiBandingMode>,
        val pictureResolutions: Set<Resolution>,
        val previewResolutions: Set<Resolution>,
        val sensorSensitivities: Set<Int>
) {

    init {
        flashModes.ensureNotEmpty()
        focusModes.ensureNotEmpty()
        antiBandingModes.ensureNotEmpty()
        previewFpsRanges.ensureNotEmpty()
        pictureResolutions.ensureNotEmpty()
        previewResolutions.ensureNotEmpty()
    }

    override fun toString(): String {
        return "Capabilities" + lineSeparator +
                "zoom:" + zoom.wrap() +
                "flashModes:" + flashModes.wrap() +
                "focusModes:" + focusModes.wrap() +
                "canSmoothZoom:" + canSmoothZoom.wrap() +
                "maxFocusAreas:" + maxFocusAreas.wrap() +
                "maxMeteringAreas:" + maxMeteringAreas.wrap() +
                "jpegQualityRange:" + jpegQualityRange.wrap() +
                "exposureCompensationRange:" + exposureCompensationRange.wrap() +
                "antiBandingModes:" + antiBandingModes.wrap() +
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
