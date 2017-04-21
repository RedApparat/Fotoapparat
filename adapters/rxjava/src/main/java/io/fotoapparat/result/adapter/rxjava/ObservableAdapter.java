package io.fotoapparat.result.adapter.rxjava;

import java.util.concurrent.Future;

import io.fotoapparat.result.adapter.Adapter;
import rx.Observable;

/**
 * Adapter for {@link Observable}.
 */
public class ObservableAdapter<T> implements Adapter<T, Observable<T>> {

    private ObservableAdapter() {
    }

    /**
     * @return {@link Adapter} which adapts result to {@link Observable}.
     */
    public static <R> ObservableAdapter<R> toObservable() {
        return new ObservableAdapter<>();
    }

    @Override
    public Observable<T> adapt(Future<T> future) {
        return Observable.from(future);
    }
}
