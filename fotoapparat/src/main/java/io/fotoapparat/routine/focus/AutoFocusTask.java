package io.fotoapparat.routine.focus;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.FocusResult;

/**
 * Tries to perform auto focus and returns result as {@link FocusResult}.
 */
public class AutoFocusTask extends FutureTask<FocusResult> {

    public AutoFocusTask(final CameraDevice cameraDevice) {
        super(new Callable<FocusResult>() {
            @Override
            public FocusResult call() throws Exception {
                return performFocus(cameraDevice);
            }
        });
    }

    @NonNull
    private static FocusResult performFocus(CameraDevice cameraDevice) {
        return cameraDevice.autoFocus().succeeded
                ? FocusResult.FOCUSED
                : FocusResult.UNABLE_TO_FOCUS;
    }

}
