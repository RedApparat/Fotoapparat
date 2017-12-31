package io.fotoapparat.selector

import io.fotoapparat.parameter.Resolution

/**
 * @return Selector function which always provides the biggest resolution.
 */
fun highestResolution(): Iterable<Resolution>.() -> Resolution? = {
    maxBy { it.area }
}

/**
 * @return Selector function which always provides the smallest resolution.
 */
fun lowestResolution(): Iterable<Resolution>.() -> Resolution? = {
    minBy { it.area }
}