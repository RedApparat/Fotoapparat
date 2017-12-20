package io.fotoapparat.configuration

import io.fotoapparat.parameter.*
import io.fotoapparat.preview.Frame
import io.fotoapparat.preview.FrameProcessor
import io.fotoapparat.selector.*


private const val DEFAULT_JPEG_QUALITY = 90

/**
 * A camera configuration which has all it's selectors defined.
 */
data class CameraConfiguration(
        override val flashMode: Iterable<Flash>.() -> Flash? = off(),
        override val focusMode: Iterable<FocusMode>.() -> FocusMode? = firstAvailable(
                continuousFocusPicture(),
                autoFocus(),
                fixed()
        ),
        override val jpegQuality: (IntRange) -> Int? = manualJpegQuality(DEFAULT_JPEG_QUALITY),
        override val frameProcessor: (Frame) -> Unit = {},
        override val previewFpsRange: Iterable<FpsRange>.() -> FpsRange? = highestFps(),
        override val antiBandingMode: Iterable<AntiBandingMode>.() -> AntiBandingMode? = firstAvailable(
                auto(),
                hz50(),
                hz60(),
                none()
        ),
        override val sensorSensitivity: (Iterable<Int>.() -> Int?)? = null,
        override val pictureResolution: Iterable<Resolution>.() -> Resolution? = highestResolution(),
        override val previewResolution: Iterable<Resolution>.() -> Resolution? = highestResolution()
) : Configuration {

    /**
     * Builder for [CameraConfiguration].
     */
    class Builder internal constructor() {

        private var cameraConfiguration: CameraConfiguration = default()

        fun flash(selector: (Iterable<Flash>.() -> Flash?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    flashMode = selector
            )
            return this
        }

        fun focusMode(selector: (Iterable<FocusMode>.() -> FocusMode?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    focusMode = selector
            )
            return this
        }

        fun previewFpsRange(selector: (Iterable<FpsRange>.() -> FpsRange?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    previewFpsRange = selector
            )
            return this
        }

        fun sensorSensitivity(selector: (Iterable<Int>.() -> Int?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    sensorSensitivity = selector
            )
            return this
        }

        fun antiBandingMode(selector: Iterable<AntiBandingMode>.() -> AntiBandingMode?): Builder {
            cameraConfiguration.copy(
                    antiBandingMode = selector
            )
            return this
        }

        fun jpegQuality(selector: IntRange.() -> Int?): Builder {
            cameraConfiguration.copy(
                    jpegQuality = selector
            )
            return this
        }

        fun previewResolution(selector: (Iterable<Resolution>.() -> Resolution?)): Builder {
            cameraConfiguration = cameraConfiguration.copy(
                    previewResolution = selector
            )
            return this
        }

        fun photoResolution(selector: (Iterable<Resolution>.() -> Resolution?)): Builder {
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
         * Alias for [CameraConfiguration.default]
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
