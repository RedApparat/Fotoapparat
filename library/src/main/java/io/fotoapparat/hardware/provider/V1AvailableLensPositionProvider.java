package io.fotoapparat.hardware.provider;

import android.hardware.Camera;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.fotoapparat.parameter.LensPosition;

/**
 * Provides available {@link LensPosition} using Camera v1 API.
 */
@SuppressWarnings("deprecation")
public class V1AvailableLensPositionProvider implements AvailableLensPositionsProvider {

    @Override
    public List<LensPosition> getAvailableLensPositions() {
        HashSet<LensPosition> positions = new HashSet<>();

        final int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);

            positions.add(
                    cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT
                            ? LensPosition.FRONT
                            : LensPosition.BACK
            );
        }

        return new ArrayList<>(positions);
    }

}
