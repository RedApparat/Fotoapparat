package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.AspectRatio;

/**
 * Selector functions for {@link io.fotoapparat.parameter.AspectRatio}.
 */
public class AspectRatioSelectors {

	/**
	 * @return {@link SelectorFunction} which provides the Standard 4:3 aspect ratio if it is
	 * available.
	 * Otherwise provides {@code null}.
	 */
	public static SelectorFunction<AspectRatio> standard() {
		return Selectors.single(AspectRatio.STANDARD_4_3);
	}

	/**
	 * @return {@link SelectorFunction} which provides the Standard 16:9 aspect ratio if it is
	 * available.
	 * Otherwise provides {@code null}.
	 */
	public static SelectorFunction<AspectRatio> wide() {
		return Selectors.single(AspectRatio.WIDE_16_9);
	}

}
