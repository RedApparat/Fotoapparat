package io.fotoapparat.parameter.selector;

import java.util.Collection;
import java.util.Collections;

import io.fotoapparat.parameter.Size;
import io.fotoapparat.util.CompareSizesByArea;

/**
 * Selector functions for {@link Size}.
 */
public class SizeSelectors {

	private static final CompareSizesByArea COMPARATOR_BY_AREA = new CompareSizesByArea();

	/**
	 * @return {@link SelectorFunction} which always provides the biggest size.
	 */
	public static SelectorFunction<Size> biggestSize() {
		return new SelectorFunction<Size>() {
			@Override
			public Size select(Collection<Size> items) {
				return Collections.max(items, COMPARATOR_BY_AREA);
			}
		};
	}

	/**
	 * @return {@link SelectorFunction} which always provides the smallest size.
	 */
	public static SelectorFunction<Size> smallestSize() {
		return new SelectorFunction<Size>() {
			@Override
			public Size select(Collection<Size> items) {
				return Collections.min(items, COMPARATOR_BY_AREA);
			}
		};
	}

}
