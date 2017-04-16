package io.fotoapparat.parameter.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.fotoapparat.parameter.AspectRatio;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.util.CompareSizesByArea;

/**
 * Selector functions for {@link Size}.
 */
public class SizeSelectors {

	private static final CompareSizesByArea COMPARATOR_BY_AREA = new CompareSizesByArea();
	private static final double ASPECT_RATIO_EPSILON = 1e-4;

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
	 * @param aspectRatioSelectorFunction The aspect ratio to be taken into responsibility.
	 * @return {@link SelectorFunction} which always provides the biggest size of the requested
	 * Aspect ratio.
	 */
	public static SelectorFunction<Size> biggestSize(final SelectorFunction<AspectRatio> aspectRatioSelectorFunction) {
		return new SelectorFunction<Size>() {
			@Override
			public Size select(Collection<Size> items) {

				AspectRatio aspectRatio = aspectRatioSelectorFunction.select(findAspectRatios(items));
				if (aspectRatio == null) {
					return null;
				}
				List<Size> filteredSizes = filterByRatio(items, aspectRatio);

				return Collections.max(filteredSizes, COMPARATOR_BY_AREA);
			}
		};
	}

	private static Collection<AspectRatio> findAspectRatios(Collection<Size> sizes) {
		List<AspectRatio> availableAspectRatios = new ArrayList<>();
		for (AspectRatio aspectRatio : AspectRatio.values()) {
			for (Size size : sizes) {
				if (aspectRatiosAreClose(size.getAspectRatio(), aspectRatio.ratioValue)) {
					availableAspectRatios.add(aspectRatio);
					break;
				}
			}
		}
		return availableAspectRatios;
	}

	private static List<Size> filterByRatio(Collection<Size> sizes, AspectRatio aspectRatio) {
		List<Size> filteredSizes = new ArrayList<>();
		for (Size size : sizes) {
			if (aspectRatiosAreClose(size.getAspectRatio(), aspectRatio.ratioValue)) {
				filteredSizes.add(size);
			}
		}
		return filteredSizes;
	}

	private static boolean aspectRatiosAreClose(float a, float b) {
		return Math.abs(a - b) < ASPECT_RATIO_EPSILON;
	}

}
