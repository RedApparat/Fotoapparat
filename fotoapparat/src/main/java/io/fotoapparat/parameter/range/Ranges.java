package io.fotoapparat.parameter.range;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Class with factory and utility methods for {@link Range}.
 */
public class Ranges {

    public static <T extends Comparable<T>> Range<T> range(@NonNull T lowerBound,
                                                           @NonNull T upperBound) {
        return new ContinuousRange<>(lowerBound, upperBound);
    }

    public static <T extends Comparable<T>> Range<T> discreteRange(@NonNull T value) {
        return discreteRange(Collections.singletonList(value));
    }

    public static <T extends Comparable<T>> Range<T> discreteRange(@NonNull Collection<T> collection) {
        List<T> list = (collection instanceof List)
                ? (List<T>) collection
                : new ArrayList<>(collection);
        Collections.sort(list);
        return new DiscreteRange<>(list);
    }

    public static <T extends Comparable<T>> Range<T> emptyRange() {
        return new EmptyRange<>();
    }

    public static <T extends Comparable<T>> boolean isEmpty(@NonNull Range<T> range) {
        return range instanceof EmptyRange;
    }
}
