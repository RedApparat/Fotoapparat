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

}
