package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.ScaleType;

/**
 * Selector functions for {@link ScaleType}.
 */
public class ScaleTypeSelectors {

    /**
     * @return {@link SelectorFunction} which provides a preview style which will be scaled so as
     * it's one dimensions will be equal and the other equal or larger than the corresponding
     * dimension of the view.
     */
    public static SelectorFunction<ScaleType> centerCropped() {
        return Selectors.single(ScaleType.CENTER_CROP);
    }

    /**
     * @return {@link SelectorFunction} which provides a preview style which will be scaled so as
     * it's one dimensions will be equal and the other equal or smaller than the corresponding
     * dimension of the view.
     */
    public static SelectorFunction<ScaleType> centerInside() {
        return Selectors.single(ScaleType.CENTER_INSIDE);
    }

}
