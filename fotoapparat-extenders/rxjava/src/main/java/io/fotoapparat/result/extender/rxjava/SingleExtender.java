package io.fotoapparat.result.extender.rxjava;

import java.util.concurrent.Future;

import io.fotoapparat.result.extender.Extender;
import rx.Single;

/**
 * Extender for {@link Single}.
 */
public class SingleExtender<T> implements Extender<T, Single<T>> {

	private SingleExtender() {
	}

	/**
	 * New instance of this extender.
	 *
	 * @return The extender.
	 */
	public static <R> SingleExtender<R> singleExtender() {
		return new SingleExtender<>();
	}

	@Override
	public Single<T> extend(Future<T> future) {
		return Single.from(future);
	}
}
