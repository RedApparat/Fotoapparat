package io.fotoapparat.routine;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.lens.FocusResultState;
import io.fotoapparat.photo.Photo;

import static io.fotoapparat.lens.FocusResultState.FAILURE;
import static io.fotoapparat.lens.FocusResultState.SUCCESS_NEEDS_EXPOSURE_MEASUREMENT;

/**
 * Takes photo and returns result as {@link Photo}.
 */
class TakePictureTask extends FutureTask<Photo> {

    TakePictureTask(final CameraDevice cameraDevice) {
        super(new Callable<Photo>() {
            @Override
            public Photo call() throws Exception {

                int focusAttempts = 0;
                FocusResultState focusResultState = FAILURE;

                while (focusAttempts < 3 && focusResultState == FAILURE) {
                    focusResultState = cameraDevice.autoFocus();
                    focusAttempts++;
                }

                if (focusResultState == SUCCESS_NEEDS_EXPOSURE_MEASUREMENT) {
                    cameraDevice.measureExposure();
                }

                Photo photo = cameraDevice.takePicture();

                cameraDevice.startPreview();

                return photo;
            }
        });
    }

}
