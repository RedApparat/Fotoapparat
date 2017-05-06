package io.fotoapparat.hardware.v2.connection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;

/**
 * Returns the {@link Characteristics} of a selected camera.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class GetCharacteristicsTask {

    private final CameraManager manager;

    GetCharacteristicsTask(CameraManager manager) {
        this.manager = manager;
    }

    /**
     * @param cameraId The desired camera id to get the {@link Characteristics}.
     * @return The {@link Characteristics} of a selected camera
     */
    public Characteristics execute(String cameraId) {
        try {
            return new Characteristics(
                    manager.getCameraCharacteristics(cameraId)
            );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }
}
