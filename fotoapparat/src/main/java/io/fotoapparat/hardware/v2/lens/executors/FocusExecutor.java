package io.fotoapparat.hardware.v2.lens.executors;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.operators.AutoFocusOperator;
import io.fotoapparat.hardware.v2.lens.operations.LensOperation;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
import io.fotoapparat.lens.FocusResultState;

/**
 * Performs a lens focus routine.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FocusExecutor implements AutoFocusOperator {
    private final LensOperationsFactory lensOperationsFactory;

    public FocusExecutor(LensOperationsFactory lensOperationsFactory) {
        this.lensOperationsFactory = lensOperationsFactory;
    }

    @Override
    public FocusResultState autoFocus() {
        LensOperation<FocusResultState> lensOperation = lensOperationsFactory.createLockFocusOperation();
        return lensOperation.call();
    }

}
