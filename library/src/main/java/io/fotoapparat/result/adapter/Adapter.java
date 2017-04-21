package io.fotoapparat.result.adapter;

import java.util.concurrent.Future;

/**
 * Adapts {@link Future} of result to another type.
 */
public interface Adapter<T, R> {

    /**
     * @return a new object type from given {@link Future} of result.
     */
    R adapt(Future<T> future);
}
