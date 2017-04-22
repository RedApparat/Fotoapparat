package io.fotoapparat.hardware.v2.selection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.parameter.LensPosition;

import static io.fotoapparat.hardware.v2.parameters.converters.LensPositionConverter.toLensFacingConstant;

/**
 * Finds the device's camera from the {@link android.hardware.camera2.CameraManager}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraSelector {

    private final android.hardware.camera2.CameraManager manager;

    public CameraSelector(android.hardware.camera2.CameraManager manager) {
        this.manager = manager;
    }

    /**
     * @param lensPosition the position of the lens relatively to the device's screen
     * @return the id of the camera as returned from the {@link android.hardware.camera2.CameraManager}
     * based on the given lens parameter
     */
    @NonNull
    public String findCameraId(LensPosition lensPosition) {
        try {
            return getCameraIdUnsafe(lensPosition);
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private String getCameraIdUnsafe(LensPosition lensPosition) throws CameraAccessException {
        String[] cameraIdList = manager.getCameraIdList();
        Integer desiredLensPosition = toLensFacingConstant(lensPosition);

        for (String cameraId : cameraIdList) {

            CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
            Integer lensFacingConstant = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);

            if (lensFacingConstant.equals(desiredLensPosition)) {
                return cameraId;
            }
        }
        throw new CameraException("No camera found with position: " + lensPosition);
    }

}
