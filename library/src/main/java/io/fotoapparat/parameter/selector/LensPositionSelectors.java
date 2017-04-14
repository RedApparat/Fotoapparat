package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.LensPosition;

/**
 * Selector functions for {@link LensPosition}.
 */
public class LensPositionSelectors {

	/**
	 * @return function which selects given {@link LensPosition} from the list if it is available.
	 * If it is not available - provides {@code null}.
	 */
	public static SelectorFunction<LensPosition> lensPosition(final LensPosition position) {
		return Selectors.single(position);
	}

}
