package io.fotoapparat.hardware.orientation


/**
 * @param screenRotationDegrees rotation of the display (as provided by system) in degrees.
 * @param cameraRotationDegrees rotation of the camera sensor relatively to device natural
 * orientation.
 * @param cameraIsMirrored `true` if camera is mirrored (typically that is the case for
 * front cameras). `false` if it is not mirrored.
 *
 * @return clockwise rotation of the image relatively to current device orientation.
 */
internal fun computeImageOrientation(
        screenRotationDegrees: Int,
        cameraRotationDegrees: Int,
        cameraIsMirrored: Boolean
): Int {
    val rotation = if (cameraIsMirrored) {
        -(screenRotationDegrees + cameraRotationDegrees)
    } else {
        screenRotationDegrees - cameraRotationDegrees
    }

    return (rotation + 720) % 360
}

/**
 * @param screenRotationDegrees rotation of the display (as provided by system) in degrees.
 * @param cameraRotationDegrees rotation of the camera sensor relatively to device natural
 * orientation.
 * @param cameraIsMirrored `true` if camera is mirrored (typically that is the case for
 * front cameras). `false` if it is not mirrored.
 *
 * @return display orientation in which user will see the output camera in a correct rotation.
 */
internal fun computeDisplayOrientation(
        screenRotationDegrees: Int,
        cameraRotationDegrees: Int,
        cameraIsMirrored: Boolean
): Int {
    var degrees = toClosestRightAngle(screenRotationDegrees)

    return if (cameraIsMirrored) {
        degrees = (cameraRotationDegrees + degrees) % 360
        (360 - degrees) % 360
    } else {
        (cameraRotationDegrees - degrees + 360) % 360
    }

}

/**
 * @return closest right angle to given value. That is: 0, 90, 180, 270.
 */
internal fun toClosestRightAngle(degrees: Int): Int {
    val roundUp = degrees % 90 > 45

    val roundAppModifier = if (roundUp) 1 else 0

    return (degrees / 90 + roundAppModifier) * 90 % 360
}
