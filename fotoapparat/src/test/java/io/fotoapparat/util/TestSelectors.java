package io.fotoapparat.util;

import java.util.Collection;

import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Selectors which are convenient for tests.
 */
public class TestSelectors {

    /**
     * @return selector which always returns given object as a result of selection.
     */
    public static <Input, Output> SelectorFunction<Input, Output> select(final Output object) {
        return new SelectorFunction<Input, Output>() {
            @Override
            public Output select(Input items) {
                return object;
            }
        };
    }

    public static <T> SelectorFunction<Collection<T>, T> selectFromCollection(final T object) {
        return new SelectorFunction<Collection<T>, T>() {
            @Override
            public T select(Collection<T> items) {
                return object;
            }
        };
    }

}
