package io.fotoapparat.selector

import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.util.CompareFpsRangeByBounds

typealias FpsRangeSelector = Iterable<FpsRange>.() -> FpsRange?

/**
 * @param fps The specified FPS
 * @return Selector function which selects FPS range that contains the specified FPS.
 * Prefers fixed rates over non fixed ones.
 */
fun containsFps(fps: Float): FpsRangeSelector = firstAvailable(
        exactFixedFps(fps),
        filtered(
                selector = highestNonFixedFps(),
                predicate = { range -> fps.toFpsIntRepresentation() in range }
        )
)

/**
 * @param fps The specified FPS
 * @return Selector function which selects FPS range that contains only the specified FPS.
 */
fun exactFixedFps(fps: Float): FpsRangeSelector = filtered(
        selector = highestFixedFps(),
        predicate = { it.min == fps.toFpsIntRepresentation() }
)

/**
 * @return Selector function which selects FPS range with max FPS.
 * Prefers non fixed rates over fixed ones.
 */
fun highestFps(): FpsRangeSelector = firstAvailable(
        highestNonFixedFps(),
        highestFixedFps()
)

/**
 * @return Selector function which selects FPS range with max FPS and non fixed rate.
 */
fun highestNonFixedFps(): FpsRangeSelector = filtered(
        selector = highestRangeFps(),
        predicate = { !it.isFixed }
)

/**
 * @return Selector function which selects FPS range with max FPS and fixed rate.
 */
fun highestFixedFps(): FpsRangeSelector = filtered(
        selector = highestRangeFps(),
        predicate = { it.isFixed }
)

/**
 * @return Selector function which selects FPS range with min FPS.
 * Prefers non fixed rates over fixed ones.
 */
fun lowestFps(): FpsRangeSelector = firstAvailable(
        lowestNonFixedFps(),
        lowestFixedFps()
)

/**
 * @return Selector function which selects FPS range with min FPS and non fixed rate.
 */
fun lowestNonFixedFps(): FpsRangeSelector = filtered(
        selector = lowestRangeFps(),
        predicate = { !it.isFixed }
)

/**
 * @return Selector function which selects FPS range with min FPS and fixed rate.
 */
fun lowestFixedFps(): FpsRangeSelector = filtered(
        selector = lowestRangeFps(),
        predicate = { it.isFixed }
)

private fun highestRangeFps(): FpsRangeSelector = { maxWith(CompareFpsRangeByBounds) }

private fun lowestRangeFps(): FpsRangeSelector = { minWith(CompareFpsRangeByBounds) }

private fun Float.toFpsIntRepresentation() = (this * 1000).toInt()