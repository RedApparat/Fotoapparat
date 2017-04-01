package io.fotoapparat.result;

/**
 * Transforms one type to another.
 */
interface Transformer<T, R> {

	/**
	 * Transforms input type to some other type.
	 *
	 * @return new type.
	 */
	R transform(T input);

}
