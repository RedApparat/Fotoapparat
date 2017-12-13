package io.fotoapparat.configuration

import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.preview.Frame
import io.fotoapparat.preview.FrameProcessor
import io.fotoapparat.selector.*

/**
 * A camera configuration which has all it's selectors defined.
 */
data class CameraConfiguration(
        override val flashMode: Collection<Flash>.() -> Flash? = off(),
        override val focusMode: Collection<FocusMode>.() -> FocusMode? = firstAvailable(
                continuousFocusPicture(),
                autoFocus(),
                fixed()
        ),
        override val frameProcessor: (Frame) -> Unit = {},
        override val previewFpsRange: Collection<FpsRange>.() -> FpsRange? = highestFps(),
        override val sensorSensitivity: (Collection<Int>.() -> Int?)? = null,
        override val pictureResolution: Collection<Resolution>.() -> Resolution? = highestResolution(),
        override val previewResolution: Collection<Resolution>.() -> Resolution? = highestResolution()
) : Configuration {

    /**
     * Builder for [CameraConfiguration].
     */
    class Builder internal constructor() {

        private var cameraConfiguration: CameraConfiguration = default()

        fun flash(selector: (Collection<Flash>.() -> Flash?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    flashMode = selector
            )
            return this
        }

        fun focusMode(selector: (Collection<FocusMode>.() -> FocusMode?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    focusMode = selector
            )
            return this
        }

        fun previewFpsRange(selector: (Collection<FpsRange>.() -> FpsRange?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    previewFpsRange = selector
            )
            return this
        }

        fun sensorSensitivity(selector: (Collection<Int>.() -> Int?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    sensorSensitivity = selector
            )
            return this
        }

        fun previewResolution(selector: (Collection<Resolution>.() -> Resolution?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    previewResolution = selector
            )
            return this
        }

        fun photoResolution(selector: (Collection<Resolution>.() -> Resolution?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    pictureResolution = selector
            )
            return this
        }

        fun frameProcessor(frameProcessor: FrameProcessor): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    frameProcessor = { frameProcessor.process(it) }
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
         * Alias for [default]
         */
        @JvmStatic
        fun standard() = default()

        /**
         * Default [CameraConfiguration].
         */
        @JvmStatic
        fun default() = CameraConfiguration()

        /**
         * Creates a new [CameraConfiguration.Builder].
         */
        @JvmStatic
        fun builder() = CameraConfiguration.Builder()
    }
}
