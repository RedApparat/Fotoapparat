package io.fotoapparat.configuration

import io.fotoapparat.selector.*

/**
 * Default camera configuration.
 */
internal fun default() = DefinedConfiguration(
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