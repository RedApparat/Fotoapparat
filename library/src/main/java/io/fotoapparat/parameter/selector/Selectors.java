package io.fotoapparat.parameter.selector;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashSet;

/**
 * General selector functions.
 */
public class Selectors {

	/**
	 * @param functions functions in order of importance.
	 * @return function which returns first non-null result from given selectors.
	 * If there are no non-null results, returns {@code null}.
	 */
	@SafeVarargs
	public static <T> SelectorFunction<T> firstAvailable(@NonNull final SelectorFunction<T> function,
														 final SelectorFunction<T>... functions) {
		return new SelectorFunction<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T select(Collection<T> items) {
				T result = function.select(items);
				if (result != null) {
					return result;
				}

				for (SelectorFunction selectorFunction : functions) {
					result = (T) selectorFunction.select(items);

					if (result != null) {
						return result;
					}
				}

				return null;
			}
		};
	}

	/**
	 * @param selector  original {@link SelectorFunction}.
	 * @param predicate condition which is checked for each value before it is passed to original
	 *                  selector.
	 * @return {@link SelectorFunction} which is called with values which are matching given
	 * condition.
	 */
	public static <T> SelectorFunction<T> filtered(@NonNull final SelectorFunction<T> selector,
												   @NonNull final Predicate<T> predicate) {
		return new SelectorFunction<T>() {
			@Override
			public T select(Collection<T> items) {
				return selector.select(
						filteredItems(items, predicate)
				);
			}
		};
	}

	private static <T> Collection<T> filteredItems(Collection<T> items, Predicate<T> predicate) {
		HashSet<T> result = new HashSet<>();

		for (T item : items) {
			if (predicate.condition(item)) {
				result.add(item);
			}
		}

		return result;
	}

	/**
	 * @param preference The desired item to be selected.
	 * @return function which returns a result if available. If the result is not available returns
	 * {@code null}.
	 */
	public static <T> SelectorFunction<T> single(final T preference) {
		return new SelectorFunction<T>() {
			@Override
			public T select(Collection<T> items) {
				return items.contains(preference)
						? preference
						: null;
			}
		};
	}

	/**
	 * @return function which always returns {@code null}.
	 */
	public static <T> SelectorFunction<T> nothing() {
		return new SelectorFunction<T>() {
			@Override
			public T select(Collection<T> items) {
				return null;
			}
		};
	}

}
