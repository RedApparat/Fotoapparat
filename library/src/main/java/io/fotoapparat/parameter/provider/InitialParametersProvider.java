package io.fotoapparat.parameter.provider;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Provides initial {@link Parameters} for {@link CameraDevice}.
 */
public class InitialParametersProvider {

    private final CameraDevice cameraDevice;
    private final SelectorFunction<FocusMode> focusModeSelector;

    public InitialParametersProvider(CameraDevice cameraDevice,
                                     SelectorFunction<FocusMode> focusModeSelector) {
        this.cameraDevice = cameraDevice;
        this.focusModeSelector = focusModeSelector;
    }

    /**
     * @return {@link Parameters} which will be used by {@link CameraDevice} on start-up.
     */
    public Parameters initialParameters() {
        Capabilities capabilities = cameraDevice.getCapabilities();

        Parameters parameters = new Parameters();

        putFocusMode(capabilities, parameters);

        return parameters;
    }

    private void putFocusMode(Capabilities capabilities, Parameters parameters) {
        parameters.putValue(
                Parameters.Type.FOCUS_MODE,
                focusModeSelector.select(
                        capabilities.supportedFocusModes()
                )
        );
    }

}
