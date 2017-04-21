package io.fotoapparat.hardware.operators;

import io.fotoapparat.parameter.Parameters;

/**
 * An interface which indicates that the class can
 * support parameter updates.
 */
public interface ParametersOperator {

    /**
     * Updates the desired parameters for the preview and the photo capture actions.
     */
    void updateParameters(Parameters parameters);
}
