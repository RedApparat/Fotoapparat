package io.fotoapparat.configuration

import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.preview.Frame

interface Configuration {
    val flashMode: (Collection<Flash>.() -> Flash?)?
    val focusMode: (Collection<FocusMode>.() -> FocusMode?)?
    val frameProcessor: ((Frame) -> Unit)?
    val previewFpsRange: (Collection<FpsRange>.() -> FpsRange?)?
    val sensorSensitivity: (Collection<Int>.() -> Int?)?
    val previewResolution: (Collection<Resolution>.() -> Resolution?)?
    val pictureResolution: (Collection<Resolution>.() -> Resolution?)?
}