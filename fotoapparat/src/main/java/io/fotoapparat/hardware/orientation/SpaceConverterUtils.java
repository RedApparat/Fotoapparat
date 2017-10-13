package io.fotoapparat.hardware.orientation;

import android.graphics.Matrix;
import android.graphics.Rect;

public class SpaceConverterUtils {

    public static Matrix getPreviewToCameraMatrix(Rect cameraRect,
                                                  int displayOrientationDegrees,
                                                  boolean cameraIsMirrored) {
        Matrix previewToCameraMatrix = new Matrix();
        Matrix cameraToPreviewMatrix = getCameraToPreviewMatrix(
                cameraRect,
                displayOrientationDegrees,
                cameraIsMirrored
        );
        cameraToPreviewMatrix.invert(previewToCameraMatrix);
        return previewToCameraMatrix;
    }

    public static Matrix getCameraToPreviewMatrix(Rect cameraRect,
                                                  int displayOrientationDegrees,
                                                  boolean cameraIsMirrored) {
        Matrix cameraToPreviewMatrix = new Matrix();

        cameraToPreviewMatrix.setScale(1, cameraIsMirrored ? -1 : 1);
        cameraToPreviewMatrix.postRotate(displayOrientationDegrees);
        cameraToPreviewMatrix.postScale(cameraRect.width() / 2000f, cameraRect.height() / 2000f);
        cameraToPreviewMatrix.postTranslate(cameraRect.centerX(), cameraRect.centerY());

        return cameraToPreviewMatrix;
    }
}
