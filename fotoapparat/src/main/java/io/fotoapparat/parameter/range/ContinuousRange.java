package io.fotoapparat.parameter.range;

import android.support.annotation.NonNull;

/**
 * Implementation of {@link Range} that represents numeric interval.
 *
 * @param <T> type of numbers in that interval.
 */
class ContinuousRange<T extends Comparable<T>> implements Range<T> {
    @NonNull private final T lowerBound;
    @NonNull private final T upperBound;

    ContinuousRange(@NonNull T lowerBound, @NonNull T upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public T highest() {
        return upperBound;
    }

    @Override
    public T lowest() {
        return lowerBound;
    }

    @Override
    public boolean contains(T value) {
        boolean gteLower = value.compareTo(lowerBound) >= 0;
        boolean lteUpper = value.compareTo(upperBound) <= 0;
        return gteLower && lteUpper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContinuousRange)) return false;

        ContinuousRange<?> that = (ContinuousRange<?>) o;

        if (!lowerBound.equals(that.lowerBound)) return false;
        return upperBound.equals(that.upperBound);
    }

    @Override
    public int hashCode() {
        int result = lowerBound.hashCode();
        result = 31 * result + upperBound.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", lowerBound, upperBound);
    }
}
