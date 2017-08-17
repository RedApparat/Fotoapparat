package io.fotoapparat.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.selector.Predicate;

/**
 * Predicate to filter ranges containing only given FPS.
 */
public class ExactFpsRangePredicate implements Predicate<Range<Integer>> {

    @NonNull private final Integer fpsBound;

    public ExactFpsRangePredicate(@NonNull Integer fpsBound) {
        this.fpsBound = fpsBound;
    }

    @Override
    public boolean condition(@Nullable Range<Integer> range) {
        return range != null
                && range.lowest().equals(range.highest())
                && range.lowest().equals(fpsBound);
    }
}
