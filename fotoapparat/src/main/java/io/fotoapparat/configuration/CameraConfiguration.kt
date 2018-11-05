package io.fotoapparat.configuration

import io.fotoapparat.selector.*
import io.fotoapparat.util.FrameProcessor
import io.fotoapparat.preview.FrameProcessor as FrameProcessorJava

private const val DEFAULT_JPEG_QUALITY = 90
private const val DEFAULT_EXPOSURE_COMPENSATION = 0

/**
 * A camera configuration which has all it's selectors defined.
 */
data class CameraConfiguration(
        override val flashMode: FlashSelector = off(),
        override val focusMode: FocusModeSelector = firstAvailable(
                continuousFocusPicture(),
                autoFocus(),
                fixed(),
                infinity()
        ),
        override val jpegQuality: QualitySelector = manualJpegQuality(DEFAULT_JPEG_QUALITY),
        override val exposureCompensation: ExposureSelector = manualExposure(DEFAULT_EXPOSURE_COMPENSATION),
        override val frameProcessor: FrameProcessor? = null,
        override val previewFpsRange: FpsRangeSelector = highestFps(),
        override val antiBandingMode: AntiBandingModeSelector = firstAvailable(
                auto(),
                hz50(),
                hz60(),
                none()
        ),
        override val sensorSensitivity: SensorSensitivitySelector? = null,
        override val pictureResolution: ResolutionSelector = highestResolution(),
        override val previewResolution: ResolutionSelector = highestResolution()
) : Configuration {

    /**
     * Builder for [CameraConfiguration].
     */
    class Builder internal constructor() {

        private var cameraConfiguration: CameraConfiguration = default()

        fun flash(selector: FlashSelector): Builder = apply {
            cameraConfiguration = cameraConfiguration.copy(
                    flashMode = selector
            )
        }

        fun focusMode(selector: FocusModeSelector): Builder = apply {
            cameraConfiguration = cameraConfiguration.copy(
                    focusMode = selector
            )
        }

        fun previewFpsRange(selector: FpsRangeSelector): Builder = apply {
            cameraConfiguration = cameraConfiguration.copy(
                    previewFpsRange = selector
            )
        }

        fun exposureCompensation(selector: ExposureSelector): Builder = apply {
            cameraConfiguration = cameraConfiguration.copy(
                    exposureCompensation = selector
            )
        }

        fun antiBandingMode(selector: AntiBandingModeSelector): Builder = apply {
            cameraConfiguration.copy(
                    antiBandingMode = selector
            )
        }

        fun jpegQuality(selector: QualitySelector): Builder = apply {
            cameraConfiguration.copy(
                    jpegQuality = selector
            )
        }

        fun sensorSensitivity(selector: SensorSensitivitySelector): Builder = apply {
            cameraConfiguration = cameraConfiguration.copy(
                    sensorSensitivity = selector
            )
        }

        fun previewResolution(selector: ResolutionSelector): Builder = apply {
            cameraConfiguration = cameraConfiguration.copy(
                    previewResolution = selector
            )
        }

        fun photoResolution(selector: ResolutionSelector): Builder = apply {
            cameraConfiguration = cameraConfiguration.copy(
                    pictureResolution = selector
            )
        }

        fun frameProcessor(frameProcessor: FrameProcessorJava?): Builder = apply {
            cameraConfiguration = cameraConfiguration.copy(
                    frameProcessor = frameProcessor?.let { it::process }
            )
        }

        /**
         * Builds a new [CameraConfiguration].
         */
        fun build(): CameraConfiguration = cameraConfiguration
    }


    companion object {

        /**
         * Alias for [CameraConfiguration.default]
         */
        @JvmStatic
        fun standard(): CameraConfiguration = default()

        /**
         * Default [CameraConfiguration].
         */
        @JvmStatic
        fun default(): CameraConfiguration = CameraConfiguration()

        /**
         * Creates a new [CameraConfiguration.Builder].
         */
        @JvmStatic
        fun builder(): Builder = Builder()
    }
}
