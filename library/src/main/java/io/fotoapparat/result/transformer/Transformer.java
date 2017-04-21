package io.fotoapparat.result.transformer;

/**
 * Transforms one type to another.
 */
public interface Transformer<T, R> {

    /**
     * Transforms input type to some other type.
     *
     * @return new type.
     */
    R transform(T input);

}
