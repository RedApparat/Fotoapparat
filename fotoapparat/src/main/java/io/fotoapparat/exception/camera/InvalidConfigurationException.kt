package io.fotoapparat.exception.camera

import io.fotoapparat.parameter.Parameter

/**
 * Thrown to indicate that a selected [Parameter] is not in the supported set.
 *
 * e.g. Camera supports flash `on`, `off` & `auto` and you ask for a `tomato`.
 */
internal class InvalidConfigurationException : CameraException {
    constructor(
            value: Any,
            klass: Class<out Parameter>,
            supportedParameters: Collection<Parameter>
    ) : super(
            "${klass.simpleName} configuration selector selected value $value. " +
                    "However it's not in the supported set of values. " +
                    "Supported parameters: $supportedParameters"
    )

    constructor(
            value: Any,
            klass: Class<out Comparable<*>>,
            supportedRange: ClosedRange<*>
    ) : super(
            "${klass.simpleName} configuration selector selected value $value. " +
                    "However it's not in the supported set of values. " +
                    "Supported parameters from: ${supportedRange.start} to ${supportedRange.endInclusive}."
    )
}