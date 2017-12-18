package io.fotoapparat.configuration

import io.fotoapparat.parameter.*
import io.fotoapparat.preview.Frame

/**
 * A camera update configuration.
 */
data class UpdateConfiguration(
        override val flashMode: (Iterable<Flash>.() -> Flash?)? = null,
        override val focusMode: (Iterable<FocusMode>.() -> FocusMode?)? = null,
        override val jpegQuality: ((IntRange) -> Int?)? = null,
        override val frameProcessor: ((Frame) -> Unit)? = null,
        override val previewFpsRange: (Iterable<FpsRange>.() -> FpsRange?)? = null,
        override val antiBandingMode: (Iterable<AntiBandingMode>.() -> AntiBandingMode?)? = null,
        override val sensorSensitivity: (Iterable<Int>.() -> Int?)? = null,
        override val previewResolution: (Iterable<Resolution>.() -> Resolution?)? = null,
        override val pictureResolution: (Iterable<Resolution>.() -> Resolution?)? = null
) : Configuration {

    /**
     * Builder for [UpdateConfiguration].
     */
    class Builder internal constructor() {

        private var configuration = UpdateConfiguration()

        fun flash(selector: Iterable<Flash>.() -> Flash?): Builder {
            configuration.copy(
                    flashMode = selector
            )
            return this
        }

        fun focusMode(selector: Iterable<FocusMode>.() -> FocusMode?): Builder {
            configuration.copy(
                    focusMode = selector
            )
            return this
        }

        fun previewFpsRange(selector: Iterable<FpsRange>.() -> FpsRange?): Builder {
            configuration.copy(
                    previewFpsRange = selector
            )
            return this
        }

        fun sensorSensitivity(selector: Iterable<Int>.() -> Int?): Builder {
            configuration.copy(
                    sensorSensitivity = selector
            )
            return this
        }

        fun antiBandingMode(selector: Iterable<AntiBandingMode>.() -> AntiBandingMode?): Builder {
            configuration.copy(
                    antiBandingMode = selector
            )
            return this
        }

        fun jpegQuality(selector: IntRange.() -> Int?): Builder {
            configuration.copy(
                    jpegQuality = selector
            )
            return this
        }

        fun previewResolution(selector: Iterable<Resolution>.() -> Resolution?): Builder {
            configuration.copy(
                    previewResolution = selector
            )
            return this
        }

        fun photoResolution(selector: Iterable<Resolution>.() -> Resolution?): Builder {
            configuration.copy(
                    pictureResolution = selector
            )
            return this
        }

        fun frameProcessor(frameProcessor: (Frame) -> Unit): Builder {
            configuration.copy(
                    frameProcessor = frameProcessor
            )
            return this
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
        fun builder() = UpdateConfiguration.Builder()
    }
}