package io.fotoapparat.result.extender;

import java.util.concurrent.Future;

/**
 * Extends functionality and returns another type.
 */
public interface Extender<T, R> {

	/**
	 * Returns a new object type after precessing a future.
	 *
	 * @return The new type.
	 */
	R extend(Future<T> future);
}
