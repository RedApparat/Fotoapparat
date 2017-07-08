package io.fotoapparat.routine.picture;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.lens.FocusResult;
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
                FocusResult focusResult = FocusResult.none();

                while (focusAttempts < 3 && !focusResult.succeeded) {
                    focusResult = cameraDevice.autoFocus();
                    focusAttempts++;
                }

                if (focusResult.needsExposureMeasurement) {
                    cameraDevice.measureExposure();
                }

                Photo photo = cameraDevice.takePicture();

                cameraDevice.startPreview();

                return photo;
            }
        });
    }

}
