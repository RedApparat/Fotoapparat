package io.fotoapparat.configuration

import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.preview.Frame

interface Configuration {
    val flashMode: (Iterable<Flash>.() -> Flash?)?
    val focusMode: (Iterable<FocusMode>.() -> FocusMode?)?
    val jpegQuality: ((IntRange) -> Int?)?
    val frameProcessor: ((Frame) -> Unit)?
    val previewFpsRange: (Iterable<FpsRange>.() -> FpsRange?)?
    val sensorSensitivity: (Iterable<Int>.() -> Int?)?
    val previewResolution: (Iterable<Resolution>.() -> Resolution?)?
    val pictureResolution: (Iterable<Resolution>.() -> Resolution?)?
}