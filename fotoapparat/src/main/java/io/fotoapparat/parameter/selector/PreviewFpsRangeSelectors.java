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

public class PreviewFpsRangeSelectors {

    private static final Comparator<Range<Integer>> COMPARATOR_BY_BOUNDS = new CompareFpsRangeByBounds();

    /** For conversion fps to fps range bound */
    private static final int FPS_RANGE_BOUNDS_SCALE = 1000;

    public static SelectorFunction<Range<Integer>> fromExactFps(Integer fps) {
        return filtered(biggestFpsRange(),
                new ExactFpsRangePredicate(fps * FPS_RANGE_BOUNDS_SCALE));
    }

    public static SelectorFunction<Range<Integer>> nearestToExactFps(Integer fps) {
        return firstAvailable(fromExactFps(fps),
                filtered(biggestFpsRange(), new InBoundsFpsRangePredicate(fps * FPS_RANGE_BOUNDS_SCALE)));
    }

    public static SelectorFunction<Range<Integer>> biggestFpsRange() {
        return new SelectorFunction<Range<Integer>>() {
            @Override
            public Range<Integer> select(Collection<Range<Integer>> items) {
                if (items.isEmpty()) {
                    return null;
                }

                return Collections.max(items, COMPARATOR_BY_BOUNDS);
            }
        };
    }

    public static SelectorFunction<Range<Integer>> lowestFpsRange() {
        return new SelectorFunction<Range<Integer>>() {
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
