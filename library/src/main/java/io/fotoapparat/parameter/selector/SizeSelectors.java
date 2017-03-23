package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.Size;

/**
 * Selector functions for {@link Size}.
 */
public class SizeSelectors {

	/**
	 * @return {@link SelectorFunction} which always provides the biggest size.
	 */
	public static SelectorFunction<Size> biggestSize() {
		return null;
	}

}
