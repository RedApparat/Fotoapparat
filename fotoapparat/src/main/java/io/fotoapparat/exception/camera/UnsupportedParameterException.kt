package io.fotoapparat.exception.camera

import io.fotoapparat.parameter.Parameter

/**
 * Thrown to indicate that a [Parameter] is not supported by the [io.fotoapparat.hardware.CameraDevice].
 */
internal class UnsupportedParameterException(
        klass: Class<out Parameter>,
        supportedParameters: Collection<Parameter>
) : CameraException(
        "Selected camera does not support the requested ${klass.simpleName} option. Supported parameters: $supportedParameters"
)