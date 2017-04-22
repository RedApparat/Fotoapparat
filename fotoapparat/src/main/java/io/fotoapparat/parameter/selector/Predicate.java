package io.fotoapparat.parameter.selector;

import android.support.annotation.Nullable;

/**
 * Checks whether given value meets some condition.
 */
public interface Predicate<T> {

    /**
     * @return {@code true} if value meets the condition. {@code false} if it doesn't.
     */
    boolean condition(@Nullable T value);

}
