package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

import io.fotoapparat.hardware.Parameters;

/**
 * Converts {@link Parameters} to {@link Camera.Parameters}.
 */
@SuppressWarnings("deprecation")
public class ParametersConverter {

    /**
     * Converts {@link Parameters} to {@link Camera.Parameters}.
     *
     * @param parameters parameters which should be converted.
     * @param output     output value. It is required because of C-style API in Camera v1.
     * @return same object which was passed as {@code output}, but filled with new parameters.
     */
    public Camera.Parameters convert(Parameters parameters, Camera.Parameters output) {
        return null;
    }

}
