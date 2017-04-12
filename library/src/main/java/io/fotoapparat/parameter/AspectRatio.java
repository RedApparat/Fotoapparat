package io.fotoapparat.parameter;

/**
 * The aspect ratio of an image describes the proportional relationship between its width and its
 * height.
 */
public enum AspectRatio {

	/**
	 * A traditional, 4:3 ratio.
	 */
	STANDARD_4_3(4f / 3f),

	/**
	 * A wider, 16:9 ratio.
	 */
	WIDE_16_9(16f / 9f);

	public final float ratioValue;

	AspectRatio(float ratioValue) {
		this.ratioValue = ratioValue;
	}
}
