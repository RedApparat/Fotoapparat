package io.fotoapparat.hardware.orientation;

/**
 * Utilities for working with device orientation.
 */
public class OrientationUtils {

    /**
     * @return closest right angle to given value. That is: 0, 90, 180, 270.
     */
    public static int toClosestRightAngle(int degrees) {
        boolean roundUp = degrees % 90 > 45;

        int roundAppModifier = roundUp ? 1 : 0;

        return (((degrees / 90) + roundAppModifier) * 90) % 360;
    }

    /**
     * @param screenRotationDegrees rotation of the display (as provided by system) in degrees.
     * @param cameraRotationDegrees rotation of the camera sensor relatively to device natural
     *                              orientation.
     * @param cameraIsMirrored      {@code true} if camera is mirrored (typically that is the case
     *                              for front cameras). {@code false} if it is not mirrored.
     * @return display orientation in which user will see the output camera in a correct rotation.
     */
    public static int computeDisplayOrientation(int screenRotationDegrees,
                                                int cameraRotationDegrees,
                                                boolean cameraIsMirrored) {
        int degrees = OrientationUtils.toClosestRightAngle(screenRotationDegrees);

        if (cameraIsMirrored) {
            degrees = (cameraRotationDegrees + degrees) % 360;
            degrees = (360 - degrees) % 360;
        } else {
            degrees = (cameraRotationDegrees - degrees + 360) % 360;
        }

        return degrees;
    }

    /**
     * @param screenRotationDegrees rotation of the display (as provided by system) in degrees.
     * @param cameraRotationDegrees rotation of the camera sensor relatively to device natural
     *                              orientation.
     * @param cameraIsMirrored      {@code true} if camera is mirrored (typically that is the case
     *                              for front cameras). {@code false} if it is not mirrored.
     * @return clockwise rotation of the image relatively to current device orientation.
     */
    public static int computeImageOrientation(int screenRotationDegrees,
                                              int cameraRotationDegrees,
                                              boolean cameraIsMirrored) {
        int rotation;

        if (cameraIsMirrored) {
            rotation = -(screenRotationDegrees + cameraRotationDegrees);
        } else {
            rotation = screenRotationDegrees - cameraRotationDegrees;
        }

        return (rotation + 360 + 360) % 360;
    }

}
