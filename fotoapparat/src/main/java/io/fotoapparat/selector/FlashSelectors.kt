package io.fotoapparat.selector

import io.fotoapparat.parameter.Flash

typealias FlashSelector = Iterable<Flash>.() -> Flash?

/**
 * @return Selector function which provides a disabled flash firing mode if available.
 * Otherwise provides `null`.
 */
fun off(): FlashSelector = single(Flash.Off)

/**
 * @return Selector function which provides a forced on flash firing mode if available.
 * Otherwise provides `null`.
 */
fun on(): FlashSelector = single(Flash.On)

/**
 * @return Selector function which provides an auto flash firing mode if available.
 * Otherwise provides `null`.
 */
fun autoFlash(): FlashSelector = single(Flash.Auto)

/**
 * @return Selector function which provides an auto flash firing mode with red eye
 * reduction if available.
 * Otherwise provides `null`.
 */
fun autoRedEye(): FlashSelector = single(Flash.AutoRedEye)

/**
 * @return Selector function which provides a torch (continuous on) flash firing mode if
 * available.
 * Otherwise provides `null`.
 */
fun torch(): FlashSelector = single(Flash.Torch)