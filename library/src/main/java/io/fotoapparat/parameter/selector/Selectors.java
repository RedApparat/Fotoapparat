package io.fotoapparat.parameter.selector;

import java.util.Collection;

/**
 * General selector functions.
 */
public class Selectors {

    /**
     * @param functions functions in order of importance.
     * @return function which returns first non-null result from given selectors.
     * If there are no non-null results, returns {@code null}.
     */
    public static <T> SelectorFunction<T> firstAvailable(final SelectorFunction<T> function,
                                                         final SelectorFunction... functions) {
        return new SelectorFunction<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public T select(Collection<T> items) {
                T result = function.select(items);
                if (result != null) {
                    return result;
                }

                for (SelectorFunction selectorFunction : functions) {
                    result = (T) selectorFunction.select(items);

                    if (result != null) {
                        return result;
                    }
                }

                return null;
            }
        };
    }

    /**
     * @return function which always returns {@code null}.
     */
    public static <T> SelectorFunction<T> nothing() {
        return new SelectorFunction<T>() {
            @Override
            public T select(Collection<T> items) {
                return null;
            }
        };
    }

}
