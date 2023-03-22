package io.fotoapparat.selector

import io.fotoapparat.parameter.Resolution

typealias ResolutionSelector = Iterable<Resolution>.() -> Resolution?

/**
 * @return Selector function which always provides the biggest resolution.
 */
fun highestResolution(): ResolutionSelector = { maxByOrNull(Resolution::area) }

/**
 * @return Selector function which always provides the smallest resolution.
 */
fun lowestResolution(): ResolutionSelector = { minByOrNull(Resolution::area) }