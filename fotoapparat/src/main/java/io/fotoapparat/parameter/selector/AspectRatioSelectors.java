package io.fotoapparat.parameter.selector;

import android.support.annotation.Nullable;

import java.util.Collection;

import io.fotoapparat.parameter.Size;

/**
 * Selector functions for various aspect ratios.
 */
public class AspectRatioSelectors {

    private static final double ASPECT_RATIO_EPSILON = 1e-4;

    /**
     * @return {@link SelectorFunction} which selects some size of standard aspect ratio (4:3).
     */
    public static SelectorFunction<Collection<Size>, Size> standardRatio(SelectorFunction<Collection<Size>, Size> selector) {
        return aspectRatio(4f / 3f, selector);
    }

    /**
     * @param selector Input selector
     * @param tolerance Aspect ratio tolerance (0.0 - 1.0)
     *
     * @return {@link SelectorFunction} which selects some size around standard aspect ratio (4:3), with given tolerance.
     */
    public static SelectorFunction<Collection<Size>, Size> standardRatio(SelectorFunction<Collection<Size>, Size> selector, double tolerance) {
        return aspectRatio(4f / 3f, selector, tolerance);
    }

    /**
     * @return {@link SelectorFunction} which selects some size of wide aspect ratio (16:9).
     */
    public static SelectorFunction<Collection<Size>, Size> wideRatio(SelectorFunction<Collection<Size>, Size> selector) {
        return aspectRatio(16f / 9f, selector);
    }

    /**
     * @param selector Input sizes, selected by provided selector function
     * @param tolerance Aspect ratio tolerance in range of 0.0 (0%) to 1.0 (100%)
     *
     * @return {@link SelectorFunction} which selects some size around wide aspect ratio (16:9), with given tolerance.
     */
    public static SelectorFunction<Collection<Size>, Size> wideRatio(SelectorFunction<Collection<Size>, Size> selector, double tolerance) {
        return aspectRatio(16f / 9f, selector, tolerance);
    }

    /**
     * Select sizes with desired aspect ratio. This selector can
     * select sizes that differ slightly from ideal aspect ratio.
     *
     * @param aspectRatio Desired aspect ratio
     * @param tolerance Aspect ratio tolerance, as 0.0 - 1.0, where 0 is exact aspect ratio and 1 allows 100% deviation
     *
     * @return {@link SelectorFunction} which selects some size around given aspect ratio.
     */
    public static SelectorFunction<Collection<Size>, Size> aspectRatio(float aspectRatio, SelectorFunction<Collection<Size>, Size> selector, double tolerance) {
        return Selectors.filtered(
                selector,
                new AspectRatioPredicate(aspectRatio, tolerance)
        );
    }

    /**
     * Select sizes with desired, exact aspect ratio.
     *
     * @return {@link SelectorFunction} which selects some size of given aspect ratio.
     */
    public static SelectorFunction<Collection<Size>, Size> aspectRatio(float aspectRatio,
                                                     SelectorFunction<Collection<Size>, Size> selector) {
        return Selectors.filtered(
                selector,
                new AspectRatioPredicate(aspectRatio, 0.0)
        );
    }

    private static class AspectRatioPredicate implements Predicate<Size> {

        private final float aspectRatio;
        private final double tolerance;

        private AspectRatioPredicate(float aspectRatio, double tolerance) {
            if(tolerance < 0.0 || tolerance > 1.0) {
                throw new IllegalArgumentException("Tolerance must be in .0 - 1.0 range");
            }
            this.aspectRatio = aspectRatio;
            this.tolerance = (aspectRatio * tolerance) + ASPECT_RATIO_EPSILON;
        }

        @Override
        public boolean condition(@Nullable Size value) {
            return value != null
                    && Math.abs(aspectRatio - value.getAspectRatio()) <= tolerance;

        }

    }

}
