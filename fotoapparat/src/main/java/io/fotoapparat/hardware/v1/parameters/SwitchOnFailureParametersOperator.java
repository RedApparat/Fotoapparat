package io.fotoapparat.hardware.v1.parameters;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.parameter.Parameters;

/**
 * Tries to execute first operator. If that fails, tries to execute the second one.
 */
public class SwitchOnFailureParametersOperator implements ParametersOperator {

    private final ParametersOperator first;
    private final ParametersOperator second;

    public SwitchOnFailureParametersOperator(ParametersOperator first,
                                             ParametersOperator second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void updateParameters(Parameters parameters) {
        try {
            first.updateParameters(parameters);
        } catch (Exception e) {
            second.updateParameters(parameters);
        }
    }
}
