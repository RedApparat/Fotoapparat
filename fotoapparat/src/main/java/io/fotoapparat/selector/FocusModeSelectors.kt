package io.fotoapparat.selector

import io.fotoapparat.parameter.FocusMode


/**
 * @return Selector function which provides a non-adjustable focus mode if available.
 * Otherwise provides `null`.
 */
fun fixed(): Iterable<FocusMode>.() -> FocusMode? {
    return single(FocusMode.Fixed)
}

/**
 * @return Selector function which provides a focus mode targeting infinity if available.
 * Otherwise provides `null`.
 */
fun infinity(): Iterable<FocusMode>.() -> FocusMode? {
    return single(FocusMode.Infinity)
}

/**
 * @return Selector function which provides a macro focus mode if available.
 * Otherwise provides `null`.
 */
fun macro(): Iterable<FocusMode>.() -> FocusMode? {
    return single(FocusMode.Macro)
}

/**
 * @return Selector function which provides an auto focus mode if available.
 * Otherwise provides `null`.
 */
fun autoFocus(): Iterable<FocusMode>.() -> FocusMode? {
    return single(FocusMode.Auto)
}

/**
 * @return Selector function which provides a focus mode which constantly tries to stay
 * in focus if available. The speed of focus change is aggressive.
 * Otherwise provides `null`.
 */
fun continuousFocusPicture(): Iterable<FocusMode>.() -> FocusMode? {
    return single(FocusMode.ContinuousFocusPicture)
}

/**
 * @return Selector function which provides a focus mode which constantly tries to stay
 * in focus if available. The speed of focus change is smooth.
 * Otherwise provides `null`.
 */
fun continuousFocusVideo(): Iterable<FocusMode>.() -> FocusMode? {
    return single(FocusMode.ContinuousFocusVideo)
}

/**
 * @return Selector function which provides a focus mode which will produce images with
 * an extended depth of field if available.
 * Otherwise provides `null`.
 */
fun edof(): Iterable<FocusMode>.() -> FocusMode? {
    return single(FocusMode.Edof)
}

