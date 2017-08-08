package io.fotoapparat.routine.parameter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.factory.ParametersFactory;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.parameter.selector.Selectors;
import io.fotoapparat.parameter.update.UpdateRequest;

import static io.fotoapparat.parameter.Parameters.combineParameters;
import static java.util.Arrays.asList;

/**
 * Updates {@link CameraDevice} parameters.
 */
// TODO write a proper test
public class UpdateParametersRoutine {

    private final CameraDevice cameraDevice;
    private final ParametersFactory parametersFactory;

    public UpdateParametersRoutine(CameraDevice cameraDevice,
                                   ParametersFactory parametersFactory) {
        this.cameraDevice = cameraDevice;
        this.parametersFactory = parametersFactory;
    }

    /**
     * Updates {@link CameraDevice} parameters.
     */
    public void updateParameters(@NonNull UpdateRequest request) {
        Capabilities capabilities = cameraDevice.getCapabilities();

        cameraDevice.updateParameters(
                combineParameters(asList(
                        flashModeParameters(request, capabilities),
                        focusModeParameters(request, capabilities)
                ))
        );
    }

    private Parameters focusModeParameters(@NonNull UpdateRequest request, Capabilities capabilities) {
        return parametersFactory.selectFocusMode(
                capabilities,
                optional(request.focusModeSelector)
        );
    }

    private Parameters flashModeParameters(@NonNull UpdateRequest request, Capabilities capabilities) {
        return parametersFactory.selectFlashMode(
                capabilities,
                optional(request.flashSelector)
        );
    }

    @NonNull
    private <T> SelectorFunction<T> optional(@Nullable SelectorFunction<T> selector) {
        return selector != null
                ? selector
                : Selectors.<T>nothing();
    }

}