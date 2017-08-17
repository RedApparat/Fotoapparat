package io.fotoapparat.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.selector.Predicate;

/**
 * Predicate to filter ranges containing given FPS.
 */
public class InBoundsFpsRangePredicate implements Predicate<Range<Integer>> {

    @NonNull
    private final Integer fpsBound;

    public InBoundsFpsRangePredicate(@NonNull Integer fpsBound) {
        this.fpsBound = fpsBound;
    }

    @Override
    public boolean condition(@Nullable Range<Integer> range) {
        return range != null && range.contains(fpsBound);
    }
}
