package io.fotoapparat.hardware.operators;

/**
 * Measures the exposure.
 */
public interface ExposureMeasurementOperator {

    /**
     * Measures the exposure. This is a blocking operation which returns when measurement completes.
     */
    void measureExposure();

}
