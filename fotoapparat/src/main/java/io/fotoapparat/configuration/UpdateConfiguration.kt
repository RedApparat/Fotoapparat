package io.fotoapparat.configuration

import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.preview.Frame

/**
 * A camera update configuration.
 */
data class UpdateConfiguration(
        override val flashMode: (Collection<Flash>.() -> Flash?)? = null,
        override val focusMode: (Collection<FocusMode>.() -> FocusMode?)? = null,
        override val frameProcessor: ((Frame) -> Unit)? = null,
        override val previewFpsRange: (Collection<FpsRange>.() -> FpsRange?)? = null,
        override val sensorSensitivity: (Collection<Int>.() -> Int?)? = null,
        override val previewResolution: (Collection<Resolution>.() -> Resolution?)? = null,
        override val pictureResolution: (Collection<Resolution>.() -> Resolution?)? = null
) : Configuration {

    /**
     * Builder for [UpdateConfiguration].
     */
    class Builder internal constructor() {

        private var configuration = UpdateConfiguration()

        fun flash(selector: Collection<Flash>.() -> Flash?): Builder {
            configuration.copy(
                    flashMode = selector
            )
            return this
        }

        fun focusMode(selector: Collection<FocusMode>.() -> FocusMode?): Builder {
            configuration.copy(
                    focusMode = selector
            )
            return this
        }

        fun previewFpsRange(selector: Collection<FpsRange>.() -> FpsRange?): Builder {
            configuration.copy(
                    previewFpsRange = selector
            )
            return this
        }

        fun sensorSensitivity(selector: Collection<Int>.() -> Int?): Builder {
            configuration.copy(
                    sensorSensitivity = selector
            )
            return this
        }

        fun previewResolution(selector: Collection<Resolution>.() -> Resolution?): Builder {
            configuration.copy(
                    previewResolution = selector
            )
            return this
        }

        fun photoResolution(selector: Collection<Resolution>.() -> Resolution?): Builder {
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