package io.fotoapparat.result.adapter.rxjava;

import java.util.concurrent.Future;

import io.fotoapparat.result.adapter.Adapter;
import rx.Completable;

/**
 * Adapter for {@link Completable}.
 */
public class CompletableAdapter<T> implements Adapter<T, Completable> {

    private CompletableAdapter() {
    }

    /**
     * @return {@link Adapter} which adapts result to {@link Completable}.
     */
    public static <R> CompletableAdapter<R> toCompletable() {
        return new CompletableAdapter<>();
    }

    @Override
    public Completable adapt(Future<T> future) {
        return Completable.fromFuture(future);
    }
}
