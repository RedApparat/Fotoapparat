package io.fotoapparat.routine.parameter;

import android.support.annotation.NonNull;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.parameter.update.UpdateRequest;

/**
 * Updates {@link CameraDevice} parameters.
 */
public class UpdateParametersRoutine {

    private final CameraDevice cameraDevice;

    public UpdateParametersRoutine(CameraDevice cameraDevice) {
        this.cameraDevice = cameraDevice;
    }

    /**
     * Updates {@link CameraDevice} parameters.
     */
    public void updateParameters(@NonNull UpdateRequest request) {

    }

}
