package io.fotoapparat.exception.camera

import io.fotoapparat.parameter.Parameter

/**
 * Thrown to indicate that a configuration selector couldn't select a value.
 */
internal class UnsupportedConfigurationException : CameraException {
    constructor(
            klass: Class<out Parameter>,
            supportedParameters: Collection<Parameter>
    ) : super(
            "${klass.simpleName} configuration selector couldn't select a value. " +
                    "Supported parameters: $supportedParameters"
    )

    constructor(
            configurationName: String,
            supportedRange: ClosedRange<*>
    ) : super(
            "$configurationName configuration selector couldn't select a value. " +
                    "Supported parameters from: ${supportedRange.start} to ${supportedRange.endInclusive}."
    )

}
