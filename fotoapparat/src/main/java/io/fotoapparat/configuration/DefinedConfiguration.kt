package io.fotoapparat.configuration

import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.preview.Frame

/**
 * A camera configuration which has all it's selectors defined.
 */
internal data class DefinedConfiguration(
        override val flashMode: Collection<Flash>.() -> Flash?,
        override val focusMode: Collection<FocusMode>.() -> FocusMode?,
        override val frameProcessor: (Frame) -> Unit,
        override val previewFpsRange: Collection<FpsRange>.() -> FpsRange?,
        override val sensorSensitivity: (Collection<Int>.() -> Int?)?,
        override val pictureResolution: Collection<Resolution>.() -> Resolution?,
        override val previewResolution: Collection<Resolution>.() -> Resolution?
) : Configuration(
        focusMode = focusMode,
        flashMode = flashMode,
        frameProcessor = frameProcessor,
        previewFpsRange = previewFpsRange,
        sensorSensitivity = sensorSensitivity,
        pictureResolution = pictureResolution,
        previewResolution = previewResolution
)
