package io.fotoapparat.hardware.v2.lens.executors;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.operators.AutoFocusOperator;
import io.fotoapparat.hardware.v2.lens.operations.LensOperation;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.lens.FocusResultState;
import io.fotoapparat.parameter.Flash;

/**
 * Performs a lens focus routine.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FocusExecutor implements AutoFocusOperator {
    private final ParametersProvider parametersProvider;
    private final LensOperationsFactory lensOperationsFactory;

    public FocusExecutor(ParametersProvider parametersProvider,
                         LensOperationsFactory lensOperationsFactory) {
        this.parametersProvider = parametersProvider;
        this.lensOperationsFactory = lensOperationsFactory;
    }

    private static FocusResultState forceExposureMetering(FocusResultState focusResultState) {
        return new FocusResultState(focusResultState.succeeded, true);
    }

    @Override
    public FocusResultState autoFocus() {
        LensOperation<FocusResultState> lensOperation = lensOperationsFactory.createLockFocusOperation();
        FocusResultState focusResultState = lensOperation.call();

        if (parametersProvider.getFlash() == Flash.ON) {
            return forceExposureMetering(focusResultState);
        }

        return focusResultState;
    }

}
