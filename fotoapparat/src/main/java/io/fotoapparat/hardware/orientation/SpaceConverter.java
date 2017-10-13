package io.fotoapparat.hardware.orientation;

import android.graphics.Matrix;
import android.graphics.Rect;

public class SpaceConverter {

    private static final int FOCUS_AREA_SIZE_DEFAULT = 100;

    private final int focusAreaSize;
    private final int displayOrientationDegrees;
    private final boolean isCameraMirrored;

    public SpaceConverter(int displayOrientationDegrees,
                          boolean isCameraMirrored) {
        this(FOCUS_AREA_SIZE_DEFAULT, displayOrientationDegrees, isCameraMirrored);
    }

    public SpaceConverter(int focusAreaSize,
                          int displayOrientationDegrees,
                          boolean isCameraMirrored) {
        this.focusAreaSize = focusAreaSize;
        this.displayOrientationDegrees = displayOrientationDegrees;
        this.isCameraMirrored = isCameraMirrored;
    }

    public Rect previewToCamera(Rect cameraRect, float previewSpaceX, float previewSpaceY) {
        Matrix previewToCameraMatrix = SpaceConverterUtils.getPreviewToCameraMatrix(
                cameraRect, displayOrientationDegrees, isCameraMirrored
        );
        float[] cameraSpaceCoords = new float[] { previewSpaceX, previewSpaceY };

        previewToCameraMatrix.mapPoints(cameraSpaceCoords);

        return getNormalizedCameraFocusRect(cameraSpaceCoords[0], cameraSpaceCoords[1]);
    }

    private Rect getNormalizedCameraFocusRect(float cameraSpaceX,
                                              float cameraSpaceY) {

        float halfSize = focusAreaSize / 2.0f;
        return new Rect(
                alignCameraSpaceCoord(cameraSpaceX - halfSize),
                alignCameraSpaceCoord(cameraSpaceY - halfSize),
                alignCameraSpaceCoord(cameraSpaceX + halfSize),
                alignCameraSpaceCoord(cameraSpaceY + halfSize)
        );
    }

    private int alignCameraSpaceCoord(float cameraSpaceCoord) {
        if (cameraSpaceCoord <= -1000) {
            return -1000;
        } else if (cameraSpaceCoord >= 1000) {
            return 1000;
        } else {
            return (int) cameraSpaceCoord;
        }
    }
}
