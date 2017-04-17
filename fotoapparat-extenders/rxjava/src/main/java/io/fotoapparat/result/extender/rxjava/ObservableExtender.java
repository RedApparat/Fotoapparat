package io.fotoapparat.result.extender.rxjava;

import java.util.concurrent.Future;

import io.fotoapparat.result.extender.Extender;
import rx.Observable;

/**
 * Extender for {@link Observable}.
 */
public class ObservableExtender<T> implements Extender<T, Observable<T>> {

	private ObservableExtender() {
	}

	/**
	 * New instance of this extender.
	 *
	 * @return The extender.
	 */
	public static <R> ObservableExtender<R> observableExtender() {
		return new ObservableExtender<>();
	}

	@Override
	public Observable<T> extend(Future<T> future) {
		return Observable.from(future);
	}
}
