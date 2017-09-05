package io.fotoapparat.parameter.range;

import android.support.annotation.Nullable;

/**
 * {@link Range} with no values in it.
 */
final class EmptyRange<T> implements Range<T> {

    @Override
    public boolean contains(T value) {
        return false;
    }

    @Override
    @Nullable
    public T highest() {
        return null;
    }

    @Override
    @Nullable
    public T lowest() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptyRange;
    }
}
