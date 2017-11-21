package io.fotoapparat.parameter.range;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * {@link Range} with no values in it.
 */
final class EmptyRange<T extends Serializable> implements Range<T> {

    private static final long serialVersionUID = 1L;

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
