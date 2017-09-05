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
public class UpdateParametersRoutine {

    private final CameraDevice cameraDevice;

    public UpdateParametersRoutine(CameraDevice cameraDevice) {
        this.cameraDevice = cameraDevice;
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
        return ParametersFactory.selectFocusMode(
                capabilities,
                optional(request.focusModeSelector)
        );
    }

    private Parameters flashModeParameters(@NonNull UpdateRequest request, Capabilities capabilities) {
        return ParametersFactory.selectFlashMode(
                capabilities,
                optional(request.flashSelector)
        );
    }

    @NonNull
    private <Input, Output> SelectorFunction<Input, Output> optional(@Nullable SelectorFunction<Input, Output> selector) {
        return selector != null
                ? selector
                : Selectors.<Input, Output>nothing();
    }

}
