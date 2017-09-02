package io.fotoapparat.parameter.range;

/**
 * Null object for {@link Range}.
 */
final class EmptyRange<T> implements Range<T> {

    @Override
    public boolean contains(T value) {
        return false;
    }

    @Override
    public T highest() {
        return null;
    }

    @Override
    public T lowest() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptyRange;
    }
}
