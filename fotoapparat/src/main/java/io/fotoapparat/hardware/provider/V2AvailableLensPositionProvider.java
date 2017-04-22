package io.fotoapparat.hardware.provider;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.parameter.LensPosition;

import static io.fotoapparat.hardware.v2.parameters.converters.LensPositionConverter.toLensPosition;

/**
 * Provides available {@link LensPosition} using Camera v2 API.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class V2AvailableLensPositionProvider implements AvailableLensPositionsProvider {

    private final CameraManager manager;

    public V2AvailableLensPositionProvider(Context context) {
        manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    @Override
    public List<LensPosition> getAvailableLensPositions() {
        Set<LensPosition> positions = new HashSet<>();

        String[] cameraIdList = getCameraIdListUnsafe();

        for (String cameraId : cameraIdList) {
            Integer lensFacingConstant = getLensPositionUnsafe(cameraId);
            positions.add(toLensPosition(lensFacingConstant));
        }

        return new ArrayList<>(positions);
    }

    private Integer getLensPositionUnsafe(String cameraId) {
        try {
            return manager
                    .getCameraCharacteristics(cameraId)
                    .get(CameraCharacteristics.LENS_FACING);
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }

    private String[] getCameraIdListUnsafe() {
        try {
            return manager.getCameraIdList();
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }
}
