package io.fotoapparat.parameter.provider;

import io.fotoapparat.parameter.Parameters;

import static io.fotoapparat.parameter.Parameters.Type.FLASH;
import static io.fotoapparat.parameter.Parameters.Type.FOCUS_MODE;
import static io.fotoapparat.parameter.Parameters.Type.PICTURE_SIZE;
import static io.fotoapparat.parameter.Parameters.Type.PREVIEW_SIZE;

/**
 * Validates that the minimum required {@link Parameters} that the camera needs to work.
 */
public class InitialParametersValidator {

    /**
     * Validates a set of {@link Parameters} which the {@link io.fotoapparat.hardware.CameraDevice}
     * needs.
     *
     * @param parameters The parameters to validate.
     */
    void validate(Parameters parameters) {
        validateParameter(parameters, PICTURE_SIZE);
        validateParameter(parameters, PREVIEW_SIZE);
        validateParameter(parameters, FOCUS_MODE);
        validateParameter(parameters, FLASH);
    }

    private static void validateParameter(Parameters parameters, Parameters.Type type) {
        if (parameters.getValue(type) == null) {
            throw new IllegalArgumentException(
                    "Opened camera does not support the selected " + type.name().toLowerCase() + " options.");
        }
    }

}
