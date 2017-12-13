package io.fotoapparat.parameter.camera.provide

import io.fotoapparat.capability.Capabilities
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.exception.camera.UnsupportedParameterException
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.parameter.Parameter
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.selector.aspectRatio
import io.fotoapparat.selector.filtered
import io.fotoapparat.selector.firstAvailable


/**
 * @return [CameraParameters] which will be used by [CameraDevice].
 */
internal fun getCameraParameters(
        capabilities: Capabilities,
        cameraConfiguration: CameraConfiguration
): CameraParameters {

    return capabilities.run {
        cameraConfiguration.run {

            val selectedPictureResolution = pictureResolution selectFrom pictureResolutions
            val validPreviewSizeSelector = validPreviewSizeSelector(
                    resolution = selectedPictureResolution,
                    original = previewResolution
            )

            CameraParameters(
                    flashMode = flashMode selectFrom flashModes,
                    focusMode = focusMode selectFrom focusModes,
                    previewFpsRange = previewFpsRange selectFrom previewFpsRanges,
                    pictureResolution = selectedPictureResolution,
                    previewResolution = validPreviewSizeSelector selectFrom previewResolutions,
                    sensorSensitivity = sensorSensitivity selectOptionalFrom sensorSensitivities
            )
        }
    }
}

private fun validPreviewSizeSelector(
        resolution: Resolution,
        original: Collection<Resolution>.() -> Resolution?
) = firstAvailable(
        filtered(
                selector = aspectRatio(
                        aspectRatio = resolution.aspectRatio,
                        selector = original
                ),
                predicate = {
                    it.area <= resolution.area
                }
        ),
        original
)

private infix fun <T> (Collection<T>.() -> T?)?.selectOptionalFrom(supportedParameters: Set<T>): T? {
    return this?.run { this(supportedParameters) }
}

private inline infix fun <reified T : Parameter> (Collection<T>.() -> T?).selectFrom(supportedParameters: Set<T>): T {
    return this(supportedParameters)
            .ensureSelected(supportedParameters)
            .ensureInCollection(supportedParameters)
}

private fun <T> T.ensureInCollection(supportedParameters: Set<T>): T {
    return if (supportedParameters.contains(this)) {
        this
    } else {
        throw IllegalArgumentException("The selected parameter is not in the supported set of values.")
    }
}

private inline fun <reified T : Parameter> T?.ensureSelected(supportedParameters: Collection<Parameter>): T {
    return this ?: throw UnsupportedParameterException(T::class.java, supportedParameters)
}
