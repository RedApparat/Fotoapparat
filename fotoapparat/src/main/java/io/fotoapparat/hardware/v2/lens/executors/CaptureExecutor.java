package io.fotoapparat.hardware.v2.lens.executors;

import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.v2.lens.operations.LensOperation;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.surface.StillSurfaceReader;
import io.fotoapparat.lens.CaptureResultState;
import io.fotoapparat.photo.Photo;

/**
 * Captures a picture.
 */
@SuppressWarnings("NewApi")
public class CaptureExecutor implements CaptureOperator {

    private final LensOperationsFactory lensOperationsFactory;
    private final StillSurfaceReader stillSurfaceReader;
    private final OrientationManager orientationManager;

    public CaptureExecutor(LensOperationsFactory lensOperationsFactory,
                           StillSurfaceReader stillSurfaceReader,
                           OrientationManager orientationManager) {
        this.lensOperationsFactory = lensOperationsFactory;
        this.stillSurfaceReader = stillSurfaceReader;
        this.orientationManager = orientationManager;
    }

    @Override
    public Photo takePicture() {
        Integer sensorOrientation = orientationManager.getSensorOrientation();
        LensOperation<CaptureResultState> captureOperation = lensOperationsFactory.createCaptureOperation(
                sensorOrientation);

        captureOperation.call();

        return new Photo(stillSurfaceReader.getPhotoBytes(), 0);
    }
}
