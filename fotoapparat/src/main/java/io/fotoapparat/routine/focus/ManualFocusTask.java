package io.fotoapparat.routine.focus;

import android.graphics.Rect;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.FocusResult;

public class ManualFocusTask extends FutureTask<FocusResult> {

    public ManualFocusTask(final CameraDevice cameraDevice,
                           final Rect cameraViewRect,
                           final float focusX, final float focusY) {
        super(new Callable<FocusResult>() {
            @Override
            public FocusResult call() throws Exception {
                return performManualFocus(cameraDevice, cameraViewRect, focusX, focusY);
            }
        });
    }

    @NonNull
    private static FocusResult performManualFocus(CameraDevice cameraDevice,
                                                  Rect cameraViewRect,
                                                  float focusX, float focusY) {
        cameraDevice.setFocusArea(cameraViewRect, focusX, focusY);
        boolean isSucceeded = cameraDevice.autoFocus().succeeded;
        if (isSucceeded) {
            cameraDevice.cancelAutoFocus();
        }
        return isSucceeded
                ? FocusResult.FOCUSED
                : FocusResult.UNABLE_TO_FOCUS;
    }

}
