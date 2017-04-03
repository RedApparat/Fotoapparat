package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

import java.util.HashSet;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.FocusMode;

/**
 * {@link Capabilities} of {@link Camera1}.
 */
@SuppressWarnings("deprecation")
public class CapabilitiesFactory {

    public Capabilities fromParameters(Camera.Parameters parameters) {
        return new Capabilities(
                extractFocusModes(parameters)
        );
    }

    private Set<FocusMode> extractFocusModes(Camera.Parameters parameters) {
        HashSet<FocusMode> result = new HashSet<>();

        for (String focusMode : parameters.getSupportedFocusModes()) {
            result.add(
                    mapFocusMode(focusMode)
            );
        }

        return result;
    }

    private FocusMode mapFocusMode(String focusMode) {
        switch (focusMode) {
            case Camera.Parameters.FOCUS_MODE_AUTO:
                return FocusMode.AUTO;
            case Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE:
                return FocusMode.CONTINUOUS_FOCUS;
            case Camera.Parameters.FOCUS_MODE_MACRO:
                return FocusMode.MACRO;
            case Camera.Parameters.FOCUS_MODE_INFINITY:
                return FocusMode.INFINITY;
            default:
                return FocusMode.FIXED;
        }
    }

}
