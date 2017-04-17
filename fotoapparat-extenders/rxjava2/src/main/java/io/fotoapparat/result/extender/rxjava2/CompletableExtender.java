package io.fotoapparat.result.extender.rxjava2;

import java.util.concurrent.Future;

import io.fotoapparat.result.extender.Extender;
import io.reactivex.Completable;

/**
 * Extender for {@link Completable}.
 */
public class CompletableExtender<T> implements Extender<T, Completable> {

	private CompletableExtender() {
	}

	/**
	 * New instance of this extender.
	 *
	 * @return The extender.
	 */
	public static <R> CompletableExtender<R> completableExtender() {
		return new CompletableExtender<>();
	}

	@Override
	public Completable extend(Future<T> future) {
		return Completable.fromFuture(future);
	}
}
