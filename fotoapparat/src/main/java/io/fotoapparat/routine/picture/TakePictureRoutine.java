package io.fotoapparat.routine.picture;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.PhotoResult;

/**
 * Takes picture.
 */
public class TakePictureRoutine {

    private final CameraDevice cameraDevice;
    private final Executor cameraExecutor;

    public TakePictureRoutine(CameraDevice cameraDevice,
                              Executor cameraExecutor) {
        this.cameraDevice = cameraDevice;
        this.cameraExecutor = cameraExecutor;
    }

    /**
     * Takes picture, returns immediately.
     *
     * @return {@link PhotoResult} which will deliver result asynchronously.
     */
    public PhotoResult takePicture() {
        TakePictureTask takePictureTask = new TakePictureTask(cameraDevice);
        cameraExecutor.execute(takePictureTask);

        return PhotoResult.fromFuture(takePictureTask);
    }

}
