package io.fotoapparat.parameter.selector;

import android.support.annotation.Nullable;

import io.fotoapparat.parameter.Size;

/**
 * Selector functions for various aspect ratios.
 */
public class AspectRatioSelectors {

    private static final double ASPECT_RATIO_EPSILON = 1e-4;

    /**
     * @return {@link SelectorFunction} which selects some size of standard aspect ratio (4:3).
     */
    public static SelectorFunction<Size> standardRatio(SelectorFunction<Size> selector) {
        return aspectRatio(4f / 3f, selector);
    }

    /**
     * @return {@link SelectorFunction} which selects some size of wide aspect ratio (16:9).
     */
    public static SelectorFunction<Size> wideRatio(SelectorFunction<Size> selector) {
        return aspectRatio(16f / 9f, selector);
    }

    /**
     * @return {@link SelectorFunction} which selects some size of given aspect ratio.
     */
    public static SelectorFunction<Size> aspectRatio(float aspectRatio,
                                                     SelectorFunction<Size> selector) {
        return Selectors.filtered(
                selector,
                new AspectRatioPredicate(aspectRatio)
        );
    }

    private static class AspectRatioPredicate implements Predicate<Size> {

        private final float aspectRatio;

        private AspectRatioPredicate(float aspectRatio) {
            this.aspectRatio = aspectRatio;
        }

        @Override
        public boolean condition(@Nullable Size value) {
            return value != null
                    && Math.abs(aspectRatio - value.getAspectRatio()) < ASPECT_RATIO_EPSILON;

        }

    }

}
