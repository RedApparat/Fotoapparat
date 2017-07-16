package io.fotoapparat.hardware.v1.parameters;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.Parameters;

/**
 * Tries to update parameters. If that fails, suppresses the exception and writes relevant
 * information to log.
 */
public class SupressExceptionsParametersOperator implements ParametersOperator {

    private final ParametersOperator wrapped;
    private final Logger logger;

    public SupressExceptionsParametersOperator(ParametersOperator wrapped,
                                               Logger logger) {
        this.wrapped = wrapped;
        this.logger = logger;
    }

    @Override
    public void updateParameters(Parameters parameters) {
        try {
            wrapped.updateParameters(parameters);
        } catch (Exception e) {
            logger.log("Unable to set parameters: " + parameters);
        }
    }

}
