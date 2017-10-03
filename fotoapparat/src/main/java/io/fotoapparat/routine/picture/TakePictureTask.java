package io.fotoapparat.routine.picture;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.lens.FocusResult;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.photo.Photo;

/**
 * Takes photo and returns result as {@link Photo}.
 */
class TakePictureTask extends FutureTask<Photo> {

    private static final int MAX_FOCUS_ATTEMPTS = 3;

    TakePictureTask(final CameraDevice cameraDevice) {
        super(new Callable<Photo>() {
            @Override
            public Photo call() throws Exception {
                adjustCameraForBestShot(cameraDevice);

                Photo photo = cameraDevice.takePicture();

                startPreviewSafe(cameraDevice);


                return photo;
            }
        });
    }

    private static void adjustCameraForBestShot(CameraDevice cameraDevice) {
        if (shouldFocus(cameraDevice)) {
            FocusResult focusResult = autoFocus(cameraDevice);

            if (focusResult.needsExposureMeasurement) {
                cameraDevice.measureExposure();
            }
        }
    }

    private static boolean shouldFocus(CameraDevice cameraDevice) {
        return currentFocusMode(cameraDevice) != FocusMode.CONTINUOUS_FOCUS;
    }

    private static Object currentFocusMode(CameraDevice cameraDevice) {
        return cameraDevice.getCurrentParameters().getValue(Parameters.Type.FOCUS_MODE);
    }

    private static FocusResult autoFocus(CameraDevice cameraDevice) {
        int focusAttempts = 0;
        FocusResult focusResult = FocusResult.none();

        while (focusAttempts < MAX_FOCUS_ATTEMPTS && !focusResult.succeeded) {
            focusResult = cameraDevice.autoFocus();
            focusAttempts++;
        }

        return focusResult;
    }

    private static void startPreviewSafe(CameraDevice cameraDevice) {
        try {
            cameraDevice.startPreview();
        } catch (CameraException e) {
            // Do nothing
        }
    }

}
