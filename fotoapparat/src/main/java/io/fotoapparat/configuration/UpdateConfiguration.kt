package io.fotoapparat.configuration

import io.fotoapparat.selector.*
import io.fotoapparat.util.FrameProcessor

/**
 * A camera update configuration.
 */
data class UpdateConfiguration(
        override val flashMode: FlashSelector? = null,
        override val focusMode: FocusModeSelector? = null,
        override val jpegQuality: QualitySelector? = null,
        override val frameProcessor: FrameProcessor? = null,
        override val previewFpsRange: FpsRangeSelector? = null,
        override val antiBandingMode: AntiBandingModeSelector? = null,
        override val sensorSensitivity: SensorSensitivitySelector? = null,
        override val previewResolution: ResolutionSelector? = null,
        override val pictureResolution: ResolutionSelector? = null
) : Configuration {

    /**
     * Builder for [UpdateConfiguration].
     */
    class Builder internal constructor() {

        private var configuration = UpdateConfiguration()

        fun flash(selector: FlashSelector): Builder = apply {
            configuration.copy(
                    flashMode = selector
            )
        }

        fun focusMode(selector: FocusModeSelector): Builder = apply {
            configuration.copy(
                    focusMode = selector
            )
        }

        fun previewFpsRange(selector: FpsRangeSelector): Builder = apply {
            configuration.copy(
                    previewFpsRange = selector
            )
        }

        fun sensorSensitivity(selector: SensorSensitivitySelector): Builder = apply {
            configuration.copy(
                    sensorSensitivity = selector
            )
        }

        fun antiBandingMode(selector: AntiBandingModeSelector): Builder = apply {
            configuration.copy(
                    antiBandingMode = selector
            )
        }

        fun jpegQuality(selector: QualitySelector): Builder = apply {
            configuration.copy(
                    jpegQuality = selector
            )
        }

        fun previewResolution(selector: ResolutionSelector): Builder = apply {
            configuration.copy(
                    previewResolution = selector
            )
        }

        fun photoResolution(selector: ResolutionSelector): Builder = apply {
            configuration.copy(
                    pictureResolution = selector
            )
        }

        fun frameProcessor(frameProcessor: FrameProcessor): Builder = apply {
            configuration.copy(
                    frameProcessor = frameProcessor
            )
        }

        /**
         * Builds a new [UpdateConfiguration].
         */
        fun build(): UpdateConfiguration = configuration
    }

    companion object {

        /**
         * Creates a new [UpdateConfiguration.Builder].
         */
        @JvmStatic
        fun builder(): Builder = Builder()
    }
}