package io.fotoapparat.configuration

import io.fotoapparat.parameter.*
import io.fotoapparat.preview.Frame

interface Configuration {
    val flashMode: (Iterable<Flash>.() -> Flash?)?
    val focusMode: (Iterable<FocusMode>.() -> FocusMode?)?
    val jpegQuality: ((IntRange) -> Int?)?
    val frameProcessor: ((Frame) -> Unit)?
    val previewFpsRange: (Iterable<FpsRange>.() -> FpsRange?)?
    val antiBandingMode: (Iterable<AntiBandingMode>.() -> AntiBandingMode?)?
    val sensorSensitivity: (Iterable<Int>.() -> Int?)?
    val previewResolution: (Iterable<Resolution>.() -> Resolution?)?
    val pictureResolution: (Iterable<Resolution>.() -> Resolution?)?
}