package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.ScaleType;

/**
 * Selector functions for {@link ScaleType}.
 */
public class ScaleTypeSelectors {

    /**
     * @return {@link ScaleType} which will make the preview to be scaled so as its one dimensions
     * will be equal and the other one equal or larger than the corresponding dimension of the
     * view.
     */
    public static ScaleType centerCropped() {
        return ScaleType.CENTER_CROP;
    }

    /**
     * @return {@link ScaleType} which will make the preview to so as its one dimensions will be
     * equal and the other one equal or smaller than the corresponding dimension of the view
     */
    public static ScaleType centerInside() {
        return ScaleType.CENTER_INSIDE;
    }

}
