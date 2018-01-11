package io.fotoapparat.configuration

import io.fotoapparat.selector.*
import io.fotoapparat.util.FrameProcessor
import io.fotoapparat.preview.FrameProcessor as FrameProcessorJava


private const val DEFAULT_JPEG_QUALITY = 90

/**
 * A camera configuration which has all it's selectors defined.
 */
data class CameraConfiguration(
        override val flashMode: FlashSelector = off(),
        override val focusMode: FocusModeSelector = firstAvailable(
                continuousFocusPicture(),
                autoFocus(),
                fixed()
        ),
        override val jpegQuality: QualitySelector = manualJpegQuality(DEFAULT_JPEG_QUALITY),
        override val frameProcessor: FrameProcessor = {},
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

        fun flash(selector: FlashSelector): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    flashMode = selector
            )
            return this
        }

        fun focusMode(selector: FocusModeSelector): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    focusMode = selector
            )
            return this
        }

        fun previewFpsRange(selector: FpsRangeSelector): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    previewFpsRange = selector
            )
            return this
        }

        fun sensorSensitivity(selector: SensorSensitivitySelector): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    sensorSensitivity = selector
            )
            return this
        }

        fun antiBandingMode(selector: AntiBandingModeSelector): Builder {
            cameraConfiguration.copy(
                    antiBandingMode = selector
            )
            return this
        }

        fun jpegQuality(selector: QualitySelector): Builder {
            cameraConfiguration.copy(
                    jpegQuality = selector
            )
            return this
        }

        fun previewResolution(selector: ResolutionSelector): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    previewResolution = selector
            )
            return this
        }

        fun photoResolution(selector: ResolutionSelector): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    pictureResolution = selector
            )
            return this
        }

        fun frameProcessor(frameProcessor: FrameProcessorJava): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    frameProcessor = frameProcessor::process
            )
            return this
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
