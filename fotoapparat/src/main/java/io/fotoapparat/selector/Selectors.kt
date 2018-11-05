package io.fotoapparat.selector

/**
 * @return Selector function which always returns `null`.
 */
fun <T> nothing(): Iterable<T>.() -> T? = {
    null
}

/**
 * @param preference The desired item to be selected.
 * @return Selector function which returns a result if available. If the result is not available returns
 * `null`.
 */
fun <T> single(preference: T): Iterable<T>.() -> T? = {
    find { it == preference }
}

/**
 * @return Selector function which selects highest [Comparable] value.
 */
fun <T : Comparable<T>> highest(): Iterable<T>.() -> T? = Iterable<T>::max

/**
 * @return Selector function which selects lowest [Comparable] value.
 */
fun <T : Comparable<T>> lowest(): Iterable<T>.() -> T? = Iterable<T>::min

/**
 * @param functions functions in order of importance.
 * @return Selector function which returns first non-null result from given selectors.
 * If there are no non-null results, returns `null`.
 */
@SafeVarargs
fun <Input, Output> firstAvailable(
        vararg functions: Input.() -> Output?
): Input.() -> Output? = {
    functions.findNonNull {
        it(this)
    }
}

/**
 * @param selector Original selector function.
 * @param predicate Condition which is checked for each value before it is passed to original
 * selector.
 * @return Selector function which is called with values which are matching given
 * condition.
 */
fun <T : Any> filtered(
        selector: Iterable<T>.() -> T?,
        predicate: (T) -> Boolean
): Iterable<T>.() -> T? = {
    selector(filter(predicate = predicate))
}

private fun <T : Any, R> Array<T>.findNonNull(selector: (T) -> R?): R? {
    forEach {
        selector(it)?.let {
            return it
        }
    }

    return null
}
