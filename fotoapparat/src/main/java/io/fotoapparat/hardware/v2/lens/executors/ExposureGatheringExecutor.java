package io.fotoapparat.hardware.v2.lens.executors;

import io.fotoapparat.hardware.operators.ExposureMeasurementOperator;
import io.fotoapparat.hardware.v2.lens.operations.LensOperation;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
import io.fotoapparat.lens.ExposureResultState;

/**
 * Performs an exposure gathering routine.
 */
@SuppressWarnings("NewApi")
public class ExposureGatheringExecutor implements ExposureMeasurementOperator {

    private final LensOperationsFactory lensOperationsFactory;

    public ExposureGatheringExecutor(LensOperationsFactory lensOperationsFactory) {
        this.lensOperationsFactory = lensOperationsFactory;
    }

    @Override
    public void measureExposure() {
        LensOperation<ExposureResultState> lensOperation = lensOperationsFactory.createExposureGatheringOperation();
        lensOperation.call();
    }
}
