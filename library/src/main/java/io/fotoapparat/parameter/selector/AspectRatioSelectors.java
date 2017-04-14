package io.fotoapparat.parameter.selector;

import java.util.Collection;

import io.fotoapparat.parameter.AspectRatio;

/**
 * Selector functions for {@link io.fotoapparat.parameter.AspectRatio}.
 */
public class AspectRatioSelectors {

	/**
	 * @return function which selects given {@link AspectRatio} from the list if it is available. If it is
	 * not available provides {@code null}.
	 */
	public static SelectorFunction<AspectRatio> aspectRatio(final AspectRatio aspectRatio) {
		return new SelectorFunction<AspectRatio>() {
			@Override
			public AspectRatio select(Collection<AspectRatio> items) {
				return items.contains(aspectRatio)
						? aspectRatio
						: null;
			}
		};
	}

}
