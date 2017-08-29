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
    public static <T> SelectorFunction<T> select(final T object) {
        return new SelectorFunction<T>() {
            @Override
            public T select(Collection<T> items) {
                return object;
            }
        };
    }

}
