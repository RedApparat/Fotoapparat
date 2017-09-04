package io.fotoapparat.parameter.range;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.fotoapparat.util.StringUtils;

/**
 * Implementation of {@link Range} that represents set of given values.
 *
 * @param <T> type of objects in that set.
 */
class DiscreteRange<T extends Comparable<? super T>> implements Range<T> {
    @NonNull private final List<T> values;

    public DiscreteRange(@NonNull Collection<T> values) {
        List<T> valuesList = (values instanceof List)
                ? (List<T>) values
                : new ArrayList<>(values);
        Collections.sort(valuesList);
        this.values = valuesList;
    }

    @Override
    public boolean contains(T value) {
        return values.contains(value);
    }

    @Override
    @Nullable
    public T highest() {
        if(values.isEmpty()) {
            return null;
        }
        return Collections.max(values);
    }

    @Override
    @Nullable
    public T lowest() {
        if(values.isEmpty()) {
            return null;
        }
        return Collections.min(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscreteRange)) return false;

        DiscreteRange<?> that = (DiscreteRange<?>) o;

        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return String.format("{%s}", StringUtils.join(", ", values));
    }
}
