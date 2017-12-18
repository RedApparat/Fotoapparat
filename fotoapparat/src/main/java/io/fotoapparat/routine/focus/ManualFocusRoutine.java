package io.fotoapparat.routine.focus;

import android.graphics.Rect;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.FocusResult;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.view.TapToFocusSupporter;

public class ManualFocusRoutine implements TapToFocusSupporter.FocusCallback {

    private final CameraDevice cameraDevice;
    private final Executor cameraExecutor;

    private PendingResult<FocusResult> manualFocusResult;

    public ManualFocusRoutine(CameraDevice cameraDevice,
                              Executor cameraExecutor) {
        this.cameraDevice = cameraDevice;
        this.cameraExecutor = cameraExecutor;
    }

    public PendingResult<FocusResult> manualFocus(Rect cameraViewRect, float focusX, float focusY) {
        ManualFocusTask manualFocusTask = new ManualFocusTask(cameraDevice, cameraViewRect, focusX, focusY);
        cameraExecutor.execute(manualFocusTask);

        return PendingResult.fromFuture(manualFocusTask);
    }

    @Override
    public void onManualFocus(final Rect cameraViewRect, float focusX, float focusY) {
        if (manualFocusResult != null) {
            manualFocusResult.cancel();
        }
        manualFocusResult = manualFocus(cameraViewRect, focusX, focusY);
        manualFocusResult.whenDone(new PendingResult.Callback<FocusResult>() {
            @Override
            public void onResult(FocusResult result) {
                cameraDevice.cancelAutoFocus();
            }
        });
    }
}
