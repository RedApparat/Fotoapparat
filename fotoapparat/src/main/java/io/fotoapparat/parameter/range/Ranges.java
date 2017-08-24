package io.fotoapparat.parameter.range;

/**
 * Class with factory methods for {@link Range}.
 */
public class Ranges {

    public static <T extends Comparable<T>> Range<T> range(T lowerBound, T upperBound) {
        return new ContinuousRange<>(lowerBound, upperBound);
    }
}
