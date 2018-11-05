package io.fotoapparat.selector

import io.fotoapparat.parameter.AntiBandingMode

typealias AntiBandingModeSelector = Iterable<AntiBandingMode>.() -> AntiBandingMode?

/**
 * @return Selector function which provides an auto anti banding mode if available.
 * Otherwise provides `null`.
 */
fun auto(): AntiBandingModeSelector = single(AntiBandingMode.Auto)


/**
 * @return Selector function which provides a 50hz banding mode if available.
 * Otherwise provides `null`.
 */
fun hz50(): AntiBandingModeSelector = single(AntiBandingMode.HZ50)

/**
 * @return Selector function which provides a 60hz anti banding mode if available.
 * Otherwise provides `null`.
 */
fun hz60(): AntiBandingModeSelector = single(AntiBandingMode.HZ60)

/**
 * @return Selector function which provides a disabled anti banding mode if available.
 * Otherwise provides `null`.
 */
fun none(): AntiBandingModeSelector = single(AntiBandingMode.None)

