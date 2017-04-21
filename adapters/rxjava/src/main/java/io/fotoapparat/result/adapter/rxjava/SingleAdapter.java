package io.fotoapparat.result.adapter.rxjava;

import java.util.concurrent.Future;

import io.fotoapparat.result.adapter.Adapter;
import rx.Single;

/**
 * Adapter for {@link Single}.
 */
public class SingleAdapter<T> implements Adapter<T, Single<T>> {

    private SingleAdapter() {
    }

    /**
     * @return {@link Adapter} which adapts result to {@link Single}.
     */
    public static <R> SingleAdapter<R> toSingle() {
        return new SingleAdapter<>();
    }

    @Override
    public Single<T> adapt(Future<T> future) {
        return Single.from(future);
    }
}
