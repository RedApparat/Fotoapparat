package io.fotoapparat.parameter.selector;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.util.CompareFpsRangeByBounds;
import io.fotoapparat.util.ExactFpsRangePredicate;
import io.fotoapparat.util.InBoundsFpsRangePredicate;

import static io.fotoapparat.parameter.selector.Selectors.filtered;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;

/**
 * Selector functions for preview FPS ranges.
 */
public class PreviewFpsRangeSelectors {

    private static final Comparator<Range<Integer>> COMPARATOR_BY_BOUNDS = new CompareFpsRangeByBounds();

    /**
     * For conversion fps to FPS range bound
     */
    private static final int FPS_RANGE_BOUNDS_SCALE = 1000;

    /**
     * @param fps the specified FPS
     * @return {@link SelectorFunction} which selects FPS range that contains only the specified FPS.
     */
    public static SelectorFunction<Collection<Range<Integer>>, Range<Integer>> fromExactFps(int fps) {
        return filtered(rangeWithHighestFps(),
                new ExactFpsRangePredicate(fps * FPS_RANGE_BOUNDS_SCALE));
    }

    /**
     * @param fps the specified FPS
     * @return {@link SelectorFunction} which selects FPS range that contains the specified FPS.
     */
    public static SelectorFunction<Collection<Range<Integer>>, Range<Integer>> nearestToExactFps(int fps) {
        return firstAvailable(fromExactFps(fps),
                filtered(rangeWithHighestFps(), new InBoundsFpsRangePredicate(fps * FPS_RANGE_BOUNDS_SCALE)));
    }

    /**
     * @return {@link SelectorFunction} which selects FPS range with max FPS.
     */
    public static SelectorFunction<Collection<Range<Integer>>, Range<Integer>> rangeWithHighestFps() {
        return new SelectorFunction<Collection<Range<Integer>>, Range<Integer>>() {
            @Override
            public Range<Integer> select(Collection<Range<Integer>> items) {
                if (items.isEmpty()) {
                    return null;
                }

                return Collections.max(items, COMPARATOR_BY_BOUNDS);
            }
        };
    }

    /**
     * @return {@link SelectorFunction} which selects FPS range with min FPS.
     */
    public static SelectorFunction<Collection<Range<Integer>>, Range<Integer>> rangeWithLowestFps() {
        return new SelectorFunction<Collection<Range<Integer>>, Range<Integer>>() {
            @Override
            public Range<Integer> select(Collection<Range<Integer>> items) {
                if (items.isEmpty()) {
                    return null;
                }

                return Collections.min(items, COMPARATOR_BY_BOUNDS);
            }
        };
    }

}
