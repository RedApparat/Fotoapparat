package io.fotoapparat.parameter.range;

import android.support.annotation.NonNull;

public class ContinuousRange<T extends Comparable<? super T>> implements Range<T> {
    @NonNull private final android.util.Range<T> range;

    public ContinuousRange(@NonNull android.util.Range<T> range) {
        this.range = range;
    }

    @Override
    public T highest() {
        return range.getUpper();
    }

    @Override
    public T lowest() {
        return range.getLower();
    }

    @Override
    public boolean contains(T value) {
        return range.contains(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContinuousRange)) return false;

        ContinuousRange<?> that = (ContinuousRange<?>) o;

        return range.equals(that.range);
    }

    @Override
    public int hashCode() {
        return range.hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", range.getLower(), range.getUpper());
    }
}
