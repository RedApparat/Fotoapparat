package io.fotoapparat.routine.focus;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.FocusResult;
import io.fotoapparat.result.PendingResult;

/**
 * Performs auto focus.
 */
public class AutoFocusRoutine {

    private final CameraDevice cameraDevice;
    private final Executor cameraExecutor;

    public AutoFocusRoutine(CameraDevice cameraDevice,
                            Executor cameraExecutor) {
        this.cameraDevice = cameraDevice;
        this.cameraExecutor = cameraExecutor;
    }

    /**
     * Perform auto focus asynchronously.
     */
    public PendingResult<FocusResult> autoFocus() {
        AutoFocusTask autoFocusTask = new AutoFocusTask(cameraDevice);
        cameraExecutor.execute(autoFocusTask);

        return PendingResult.fromFuture(autoFocusTask);
    }

}
