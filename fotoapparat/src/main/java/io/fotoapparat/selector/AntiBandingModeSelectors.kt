package io.fotoapparat.selector

import io.fotoapparat.parameter.AntiBandingMode


/**
 * @return Selector function which provides an auto anti banding mode if available.
 * Otherwise provides `null`.
 */
fun auto(): Iterable<AntiBandingMode>.() -> AntiBandingMode? {
    return single(AntiBandingMode.Auto)
}

/**
 * @return Selector function which provides a 50hz banding mode if available.
 * Otherwise provides `null`.
 */
fun hz50(): Iterable<AntiBandingMode>.() -> AntiBandingMode? {
    return single(AntiBandingMode.HZ50)
}

/**
 * @return Selector function which provides a 60hz anti banding mode if available.
 * Otherwise provides `null`.
 */
fun hz60(): Iterable<AntiBandingMode>.() -> AntiBandingMode? {
    return single(AntiBandingMode.HZ60)
}

/**
 * @return Selector function which provides a disabled anti banding mode if available.
 * Otherwise provides `null`.
 */
fun none(): Iterable<AntiBandingMode>.() -> AntiBandingMode? {
    return single(AntiBandingMode.None)
}

