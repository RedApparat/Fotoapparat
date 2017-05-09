package io.fotoapparat.hardware.v2.lens.executors;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.operators.AutoFocusOperator;
import io.fotoapparat.hardware.v2.lens.operations.LensOperation;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.lens.FocusResult;
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

    private static FocusResult forceExposureMetering(FocusResult focusResult) {
        return new FocusResult(focusResult.succeeded, true);
    }

    @Override
    public FocusResult autoFocus() {
        LensOperation<FocusResult> lensOperation = lensOperationsFactory.createLockFocusOperation();
        FocusResult focusResult = lensOperation.call();

        if (parametersProvider.getFlash() == Flash.ON) {
            return forceExposureMetering(focusResult);
        }

        return focusResult;
    }

}
