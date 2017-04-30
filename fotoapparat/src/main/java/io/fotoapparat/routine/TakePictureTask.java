package io.fotoapparat.routine;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.photo.Photo;
import io.fotoapparat.result.FocusResultState;

import static io.fotoapparat.result.FocusResultState.FAILUTE;
import static io.fotoapparat.result.FocusResultState.SUCCESS_NEEDS_EXPOSURE_MEASUREMENT;

/**
 * Takes photo and returns result as {@link Photo}.
 */
class TakePictureTask extends FutureTask<Photo> {

    public TakePictureTask(final CameraDevice cameraDevice) {
        super(new Callable<Photo>() {
            @Override
            public Photo call() throws Exception {

                int focusAttempts = 0;
                FocusResultState focusResultState = FAILUTE;

                while (focusAttempts < 3 && focusResultState == FAILUTE) {
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
