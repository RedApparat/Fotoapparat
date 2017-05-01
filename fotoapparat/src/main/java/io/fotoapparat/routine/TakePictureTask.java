package io.fotoapparat.routine;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.lens.FocusResultState;
import io.fotoapparat.photo.Photo;

/**
 * Takes photo and returns result as {@link Photo}.
 */
class TakePictureTask extends FutureTask<Photo> {

    TakePictureTask(final CameraDevice cameraDevice) {
        super(new Callable<Photo>() {
            @Override
            public Photo call() throws Exception {

                int focusAttempts = 0;
                FocusResultState focusResultState = FocusResultState.none();

                while (focusAttempts < 3 && !focusResultState.succeeded) {
                    focusResultState = cameraDevice.autoFocus();
                    focusAttempts++;
                }

                if (focusResultState.needsExposureMeasurement) {
                    cameraDevice.measureExposure();
                }

                // cameraDevice.stopPreview(); // todo can we do it in camera1?
                Photo photo = cameraDevice.takePicture();

                cameraDevice.startPreview();

                return photo;
            }
        });
    }

}
