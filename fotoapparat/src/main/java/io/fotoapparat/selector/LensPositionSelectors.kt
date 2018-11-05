package io.fotoapparat.selector

import io.fotoapparat.characteristic.LensPosition

typealias LensPositionSelector = Iterable<LensPosition>.() -> LensPosition?

/**
 * @return Selector function which provides the front camera if it is available.
 * Otherwise provides `null`.
 */
fun front(): LensPositionSelector = single(LensPosition.Front)

/**
 * @return Selector function which provides the back camera if it is available.
 * Otherwise provides `null`.
 */
fun back(): LensPositionSelector = single(LensPosition.Back)

/**
 * @return Selector function which provides the external camera if it is available.
 * Otherwise provides `null`.
 */
fun external(): LensPositionSelector = single(LensPosition.External)
