package io.fotoapparat.selector

import io.fotoapparat.parameter.Flash


/**
 * @return Selector function which provides a disabled flash firing mode if available.
 * Otherwise provides `null`.
 */
fun off(): Iterable<Flash>.() -> Flash? {
    return single(Flash.Off)
}

/**
 * @return Selector function which provides a forced on flash firing mode if available.
 * Otherwise provides `null`.
 */
fun on(): Iterable<Flash>.() -> Flash? {
    return single(Flash.On)
}

/**
 * @return Selector function which provides an auto flash firing mode if available.
 * Otherwise provides `null`.
 */
fun autoFlash(): Iterable<Flash>.() -> Flash? {
    return single(Flash.Auto)
}

/**
 * @return Selector function which provides an auto flash firing mode with red eye
 * reduction if available.
 * Otherwise provides `null`.
 */
fun autoRedEye(): Iterable<Flash>.() -> Flash? {
    return single(Flash.AutoRedEye)
}

/**
 * @return Selector function which provides a torch (continuous on) flash firing mode if
 * available.
 * Otherwise provides `null`.
 */
fun torch(): Iterable<Flash>.() -> Flash? {
    return single(Flash.Torch)
}