package io.fotoapparat.parameter.camera.provide

import io.fotoapparat.capability.Capabilities
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.exception.camera.InvalidConfigurationException
import io.fotoapparat.exception.camera.UnsupportedConfigurationException
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.parameter.Parameter
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.selector.*

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
                    jpegQuality = jpegQuality selectFrom jpegQualityRange,
                    exposureCompensation = exposureCompensation selectFrom exposureCompensationRange,
                    previewFpsRange = previewFpsRange selectFrom previewFpsRanges,
                    antiBandingMode = antiBandingMode selectFrom antiBandingModes,
                    pictureResolution = selectedPictureResolution,
                    previewResolution = validPreviewSizeSelector selectFrom previewResolutions,
                    sensorSensitivity = sensorSensitivity selectOptionalFrom sensorSensitivities
            )
        }
    }
}

private fun validPreviewSizeSelector(
        resolution: Resolution,
        original: ResolutionSelector
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

private infix fun <T> (Collection<T>.() -> T?)?.selectOptionalFrom(supportedParameters: Set<T>): T? =
        this?.run { this(supportedParameters) }

private inline infix fun <reified T : Parameter> (Collection<T>.() -> T?).selectFrom(
        supportedParameters: Set<T>
): T = this(supportedParameters)
        .ensureSelected(supportedParameters)
        .ensureInCollection(supportedParameters)


private infix fun QualitySelector.selectFrom(supportedParameters: IntRange): Int =
        this(supportedParameters)
                .ensureSelected(
                        supportedParameters = supportedParameters,
                        configurationName = "Jpeg quality"
                )
                .ensureInCollection(supportedParameters)

private inline fun <reified Param : Parameter> Param.ensureInCollection(
        supportedParameters: Set<Param>
): Param = apply {
    if (this !in supportedParameters) {
        throw InvalidConfigurationException(
                value = this,
                klass = Param::class.java,
                supportedParameters = supportedParameters
        )
    }
}

private inline fun <reified Param : Comparable<Param>> Param.ensureInCollection(
        supportedRange: ClosedRange<Param>
): Param = apply {
    if (this !in supportedRange) {
        throw InvalidConfigurationException(
                value = this,
                klass = Param::class.java,
                supportedRange = supportedRange
        )
    }
}


private inline fun <reified Param : Parameter> Param?.ensureSelected(
        supportedParameters: Collection<Parameter>
): Param = this ?: throw UnsupportedConfigurationException(
        klass = Param::class.java,
        supportedParameters = supportedParameters
)

private inline fun <reified Param : Comparable<Param>> Param?.ensureSelected(
        supportedParameters: ClosedRange<Param>,
        configurationName: String
): Param = this ?: throw UnsupportedConfigurationException(
        configurationName = configurationName,
        supportedRange = supportedParameters
)
