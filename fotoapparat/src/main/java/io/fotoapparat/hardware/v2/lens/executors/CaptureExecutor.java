package io.fotoapparat.hardware.v2.lens.executors;

import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.v2.lens.operations.LensOperation;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
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

    public CaptureExecutor(LensOperationsFactory lensOperationsFactory,
                           StillSurfaceReader stillSurfaceReader) {
        this.lensOperationsFactory = lensOperationsFactory;
        this.stillSurfaceReader = stillSurfaceReader;
    }

    @Override
    public Photo takePicture() {
        LensOperation<CaptureResultState> captureOperation = lensOperationsFactory.createCaptureOperation();

        captureOperation.call();

        return new Photo(stillSurfaceReader.getPhotoBytes(), 0);
    }
}
