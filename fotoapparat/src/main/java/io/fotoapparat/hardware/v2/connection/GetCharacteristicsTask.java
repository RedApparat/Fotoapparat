package io.fotoapparat.hardware.v2.connection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.concurrent.Callable;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;

/**
 * Returns the {@link Characteristics} of a selected camera.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class GetCharacteristicsTask implements Callable<Characteristics> {

    private final CameraManager manager;
    private final String cameraId;

    GetCharacteristicsTask(CameraManager manager,
                           String cameraId) {
        this.manager = manager;
        this.cameraId = cameraId;
    }

    @Override
    public Characteristics call() {
        try {
            return new Characteristics(
                    manager.getCameraCharacteristics(cameraId)
            );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }
}
