package io.fotoapparat.hardware.v2.lens.executors;

import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.v2.lens.operations.LensOperation;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.readers.StillSurfaceReader;
import io.fotoapparat.lens.CaptureResultState;
import io.fotoapparat.photo.Photo;

/**
 * Captures a picture.
 */
@SuppressWarnings("NewApi")
public class CaptureOperatorImpl implements CaptureOperator {

    private final LensOperationsFactory lensOperationsFactory;
    private final StillSurfaceReader stillSurfaceReader;
    private final OrientationManager orientationManager;

    public CaptureOperatorImpl(LensOperationsFactory lensOperationsFactory,
                               StillSurfaceReader stillSurfaceReader,
                               OrientationManager orientationManager) {
        this.lensOperationsFactory = lensOperationsFactory;
        this.stillSurfaceReader = stillSurfaceReader;
        this.orientationManager = orientationManager;
    }

    @Override
    public Photo takePicture() {
        LensOperation<CaptureResultState> captureOperation = lensOperationsFactory.createCaptureOperation();

        captureOperation.call();

        return new Photo(
                stillSurfaceReader.getPhotoBytes(),
                orientationManager.getPhotoOrientation()
        );
    }
}
