package io.fotoapparat.hardware.v1.parameters;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.parameter.Parameters;

/**
 * Instead of updating all parameters at once, updates each parameter one by one.
 */
public class SplitParametersOperator implements ParametersOperator {

    private final ParametersOperator wrapped;

    public SplitParametersOperator(ParametersOperator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void updateParameters(Parameters parameters) {
        for (Parameters.Type type : parameters.storedTypes()) {
            wrapped.updateParameters(
                    newParameters(type, parameters.getValue(type))
            );
        }
    }

    private Parameters newParameters(Parameters.Type type, Object value) {
        Parameters parameters = new Parameters();
        parameters.putValue(type, value);

        return parameters;
    }

}
