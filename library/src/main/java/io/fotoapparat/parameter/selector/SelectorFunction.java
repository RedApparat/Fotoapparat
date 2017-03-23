package io.fotoapparat.parameter.selector;

import java.util.Collection;

/**
 * Function which selects one element from many.
 */
public interface SelectorFunction<T> {

	/**
	 * @return element from given collection.
	 */
	T select(Collection<T> items);

}
