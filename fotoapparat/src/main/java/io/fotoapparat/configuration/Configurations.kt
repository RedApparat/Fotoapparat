package io.fotoapparat.configuration

import io.fotoapparat.selector.*

/**
 * Default camera configuration.
 */
internal fun default() = CameraConfiguration(
        focusMode = firstAvailable(
                continuousFocusPicture(),
                autoFocus(),
                fixed()
        ),
        flashMode = off(),
        frameProcessor = {},
        previewFpsRange = highestFps(),
        sensorSensitivity = null,
        pictureResolution = highestResolution(),
        previewResolution = highestResolution()
)