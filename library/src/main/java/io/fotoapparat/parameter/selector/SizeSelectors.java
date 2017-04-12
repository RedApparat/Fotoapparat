package io.fotoapparat.parameter.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.fotoapparat.parameter.AspectRatio;
import io.fotoapparat.parameter.Size;

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
	 * @param aspectRatio The aspect ratio to be taken into responsibility.
	 * @return {@link SelectorFunction} which always provides the biggest size of the requested
	 * Aspect ratio.
	 */
	public static SelectorFunction<Size> biggestSize(final AspectRatio aspectRatio) {
		return new SelectorFunction<Size>() {
			@Override
			public Size select(Collection<Size> items) {
				return Collections.max(filterByRatio(items, aspectRatio), COMPARATOR_BY_AREA);
			}
		};
	}

	private static List<Size> filterByRatio(Collection<Size> sizes, AspectRatio aspectRatio) {

		List<Size> filteredSizes = new ArrayList<>();
		for (Size size : sizes) {

			float sizeAspectRatio = Math.round((float) size.width / size.height * 10000f) / 10000f;

			if (Float.compare(sizeAspectRatio, aspectRatio.ratioValue) == 0) {
				filteredSizes.add(size);
			}

		}
		return filteredSizes;
	}

	/**
	 * Comparator based on area of the given {@link Size} objects.
	 */
	private static class CompareSizesByArea implements Comparator<Size> {

		@Override
		public int compare(Size lhs, Size rhs) {
			return Long.signum((long) lhs.width * lhs.height -
					(long) rhs.width * rhs.height);
		}
	}

}
