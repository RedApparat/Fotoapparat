package io.fotoapparat.selector

import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.util.CompareFpsRangeByBounds

/**
 * @param fps The specified FPS
 * @return Selector function which selects FPS range that contains the specified FPS.
 * Prefers fixed rates over non fixed ones.
 */
fun containsFps(fps: Float): Iterable<FpsRange>.() -> FpsRange? = firstAvailable(
        exactFixedFps(fps),
        filtered(
                selector = highestNonFixedFps(),
                predicate = {
                    it.contains(fps.toFpsIntRepresentation())
                }
        )
)

/**
 * @param fps The specified FPS
 * @return Selector function which selects FPS range that contains only the specified FPS.
 */
fun exactFixedFps(fps: Float): Iterable<FpsRange>.() -> FpsRange? = filtered(
        selector = highestFixedFps(),
        predicate = {
            it.min == fps.toFpsIntRepresentation()
        }
)

/**
 * @return Selector function which selects FPS range with max FPS.
 * Prefers non fixed rates over fixed ones.
 */
fun highestFps(): Iterable<FpsRange>.() -> FpsRange? = firstAvailable(
        highestNonFixedFps(),
        highestFixedFps()
)

/**
 * @return Selector function which selects FPS range with max FPS and non fixed rate.
 */
fun highestNonFixedFps(): Iterable<FpsRange>.() -> FpsRange? = filtered(
        selector = highestRangeFps(),
        predicate = { !it.isFixed }
)

/**
 * @return Selector function which selects FPS range with max FPS and fixed rate.
 */
fun highestFixedFps(): Iterable<FpsRange>.() -> FpsRange? = filtered(
        selector = highestRangeFps(),
        predicate = { it.isFixed }
)

/**
 * @return Selector function which selects FPS range with min FPS.
 * Prefers non fixed rates over fixed ones.
 */
fun lowestFps(): Iterable<FpsRange>.() -> FpsRange? = firstAvailable(
        lowestNonFixedFps(),
        lowestFixedFps()
)

/**
 * @return Selector function which selects FPS range with min FPS and non fixed rate.
 */
fun lowestNonFixedFps(): Iterable<FpsRange>.() -> FpsRange? {
    return filtered(
            selector = lowestRangeFps(),
            predicate = { !it.isFixed }
    )
}

/**
 * @return Selector function which selects FPS range with min FPS and fixed rate.
 */
fun lowestFixedFps(): Iterable<FpsRange>.() -> FpsRange? {
    return filtered(
            selector = lowestRangeFps(),
            predicate = { it.isFixed }
    )
}

private fun highestRangeFps(): Iterable<FpsRange>.() -> FpsRange? = {
    maxWith(CompareFpsRangeByBounds)
}

private fun lowestRangeFps(): Iterable<FpsRange>.() -> FpsRange? = {
    minWith(CompareFpsRangeByBounds)
}

private fun Float.toFpsIntRepresentation() = (this * 1000).toInt()