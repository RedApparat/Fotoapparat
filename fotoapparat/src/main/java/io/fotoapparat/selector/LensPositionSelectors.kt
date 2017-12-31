package io.fotoapparat.selector

import io.fotoapparat.characteristic.LensPosition


/**
 * @return Selector function which provides the front camera if it is available.
 * Otherwise provides `null`.
 */
fun front(): Iterable<LensPosition>.() -> LensPosition? {
    return single(LensPosition.Front)
}

/**
 * @return Selector function which provides the back camera if it is available.
 * Otherwise provides `null`.
 */
fun back(): Iterable<LensPosition>.() -> LensPosition? {
    return single(LensPosition.Back)
}

/**
 * @return Selector function which provides the external camera if it is available.
 * Otherwise provides `null`.
 */
fun external(): Iterable<LensPosition>.() -> LensPosition? {
    return single(LensPosition.External)
}