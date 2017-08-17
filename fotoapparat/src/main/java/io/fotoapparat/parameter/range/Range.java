package io.fotoapparat.parameter.range;

public interface Range<T> {
    T highest();
    T lowest();
    boolean contains(T value);
}
