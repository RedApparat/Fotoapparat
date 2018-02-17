@file:Suppress("DEPRECATION")

package io.fotoapparat.capability.provide

import android.hardware.Camera
import io.fotoapparat.capability.Capabilities
import io.fotoapparat.parameter.SupportedParameters
import io.fotoapparat.parameter.camera.convert.*

/**
 * Returns the [io.fotoapparat.capability.Capabilities] of the given [Camera].
 */
internal fun Camera.getCapabilities() = SupportedParameters(parameters).getCapabilities()

private fun SupportedParameters.getCapabilities(): Capabilities {
    return Capabilities(
            zoom = supportedZoom,
            flashModes = flashModes.extract { it.toFlash() },
            focusModes = focusModes.extract { it.toFocusMode() },
            maxFocusAreas = maxNumFocusAreas,
            canSmoothZoom = supportedSmoothZoom,
            maxMeteringAreas = maxNumMeteringAreas,
            jpegQualityRange = jpegQualityRange,
            exposureCompensationRange = exposureCompensationRange,
            antiBandingModes = supportedAutoBandingModes.extract(String::toAntiBandingMode),
            sensorSensitivities = sensorSensitivities.toSet(),
            previewFpsRanges = supportedPreviewFpsRanges.extract { it.toFpsRange() },
            pictureResolutions = pictureResolutions.mapSizes(),
            previewResolutions = previewResolutions.mapSizes()
    )
}

private fun <Parameter : Any, Code> List<Code>.extract(converter: (Code) -> Parameter?) = mapNotNull { converter(it) }.toSet()

private fun Collection<Camera.Size>.mapSizes() = map { it.toResolution() }.toSet()