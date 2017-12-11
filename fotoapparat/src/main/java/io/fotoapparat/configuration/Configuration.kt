package io.fotoapparat.configuration

import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.preview.Frame

/**
 * A camera configuration.
 */
open class Configuration(
        open val flashMode: (Collection<Flash>.() -> Flash?)? = null,
        open val focusMode: (Collection<FocusMode>.() -> FocusMode?)? = null,
        open val frameProcessor: ((Frame) -> Unit)? = null,
        open val previewFpsRange: (Collection<FpsRange>.() -> FpsRange?)? = null,
        open val sensorSensitivity: (Collection<Int>.() -> Int?)? = null,
        open val previewResolution: (Collection<Resolution>.() -> Resolution?)? = null,
        open val pictureResolution: (Collection<Resolution>.() -> Resolution?)? = null
)