package io.fotoapparat.parameter.range;

class EmptyRange<T> implements Range<T> {

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
}
