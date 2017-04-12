package io.fotoapparat.util;

/**
 * Utils around {@link Float} functionality.
 */
public class FloatUtils {

	private static final float DECIMAL_MULTIPLIER = 10000f;

	private static float roundFloat(float inputFloat) {
		return Math.round(inputFloat * DECIMAL_MULTIPLIER) / DECIMAL_MULTIPLIER;
	}

	/**
	 * Naively compares the two floats up to the 4th decimal.
	 *
	 * @param firstFloat  The first float to compare.
	 * @param secondFloat The second float to compare.
	 * @return {@code true} if floats are same.
	 */
	public static boolean areEqual(float firstFloat, float secondFloat) {
		float roundedFirstFloat = roundFloat(firstFloat);
		float roundedSecondFloat = roundFloat(secondFloat);
		return Float.compare(roundedFirstFloat, roundedSecondFloat) == 0;
	}
}
