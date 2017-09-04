package io.fotoapparat.parameter.range;

import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * Class with factory and utility methods for {@link Range}.
 */
public class Ranges {

    /**
     * @return instance of {@link ContinuousRange} with set lower and upper bounds.
     */
    public static <T extends Comparable<T>> Range<T> continuousRange(@NonNull T lowerBound,
                                                                     @NonNull T upperBound) {
        return new ContinuousRange<>(lowerBound, upperBound);
    }

    /**
     * @return instance of {@link ContinuousRange} with same lower and upper bounds,
     *         that equal given value.
     */
    public static <T extends Comparable<T>> Range<T> continuousRange(@NonNull T value) {
        return new ContinuousRange<>(value, value);
    }

    /**
     * @return instance of {@link DiscreteRange} with given values from collection.
     */
    public static <T extends Comparable<T>> Range<T> discreteRange(@NonNull Collection<T> collection) {
        return new DiscreteRange<>(collection);
    }

    /**
     * @return instance of {@link EmptyRange}.
     */
    public static <T extends Comparable<T>> Range<T> emptyRange() {
        return new EmptyRange<>();
    }

    /**
     * Returns <tt>true</tt>, if given range is empty. Otherwise, <tt>false</tt>.
     */
    public static <T extends Comparable<T>> boolean isEmpty(@NonNull Range<T> range) {
        return range instanceof EmptyRange;
    }
}
