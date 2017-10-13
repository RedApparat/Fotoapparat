package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.FocusMode;

import static io.fotoapparat.hardware.v1.capabilities.FocusCapability.toCode;
import static io.fotoapparat.hardware.v1.capabilities.FocusCapability.toFocusMode;

public class FocusUpdater {

    private static final List<FocusMode> ACCEPTABLE_FOR_MANUAL_FOCUS_MODES = Arrays.asList(
            FocusMode.AUTO, FocusMode.MACRO, FocusMode.CONTINUOUS_FOCUS
    );

    private final CameraParametersDecorator parameters;
    private final Capabilities capabilities;

    public FocusUpdater(CameraParametersDecorator parameters,
                        Capabilities capabilities) {
        this.parameters = parameters;
        this.capabilities = capabilities;
    }

    public CameraParametersDecorator getParameters() {
        return parameters;
    }

    /**
     * @return whether or not camera should autoFocus after this
     */
    public boolean setManualFocus(List<Camera.Area> focusingAreas) {
        Set<FocusMode> supportedFocusModes = capabilities.supportedFocusModes();
        FocusMode currentFocusMode = toFocusMode(parameters.getFocusMode());

        if (supportedFocusModes.contains(FocusMode.AUTO)
                && parameters.getMaxNumFocusAreas() != 0
                && ACCEPTABLE_FOR_MANUAL_FOCUS_MODES.contains(currentFocusMode)) {
            parameters.setFocusMode(toCode(FocusMode.AUTO));
            parameters.setFocusAreas(focusingAreas);

            if (parameters.getMaxNumMeteringAreas() > 0) {
                parameters.setMeteringAreas(focusingAreas);
            }

            return true;
        } else if (parameters.getMaxNumMeteringAreas() != 0) {
            if (parameters.getMaxNumMeteringAreas() > 0) {
                parameters.setMeteringAreas(focusingAreas);
            }

            return true;
        }

        return false;
    }


}
