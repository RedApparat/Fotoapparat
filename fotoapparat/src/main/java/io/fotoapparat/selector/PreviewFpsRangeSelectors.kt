package io.fotoapparat.selector

import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.util.CompareFpsRangeByBounds

/**
 * @param fps The specified FPS
 * @return Selector function which selects FPS range that contains the specified FPS.
 * Prefers fixed rates over non fixed ones.
 */
fun containsFps(fps: Float): Collection<FpsRange>.() -> FpsRange? = firstAvailable(
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
fun exactFixedFps(fps: Float): Collection<FpsRange>.() -> FpsRange? = filtered(
        selector = highestFixedFps(),
        predicate = {
            it.min == fps.toFpsIntRepresentation()
        }
)

/**
 * @return Selector function which selects FPS range with max FPS.
 * Prefers non fixed rates over fixed ones.
 */
fun highestFps(): Collection<FpsRange>.() -> FpsRange? = firstAvailable(
        highestNonFixedFps(),
        highestFixedFps()
)

/**
 * @return Selector function which selects FPS range with max FPS and non fixed rate.
 */
fun highestNonFixedFps(): Collection<FpsRange>.() -> FpsRange? = filtered(
        selector = highestRangeFps(),
        predicate = { !it.isFixed }
)

/**
 * @return Selector function which selects FPS range with max FPS and fixed rate.
 */
fun highestFixedFps(): Collection<FpsRange>.() -> FpsRange? = filtered(
        selector = highestRangeFps(),
        predicate = { it.isFixed }
)

/**
 * @return Selector function which selects FPS range with min FPS.
 * Prefers non fixed rates over fixed ones.
 */
fun lowestFps(): Collection<FpsRange>.() -> FpsRange? = firstAvailable(
        lowestNonFixedFps(),
        lowestFixedFps()
)

/**
 * @return Selector function which selects FPS range with min FPS and non fixed rate.
 */
fun lowestNonFixedFps(): Collection<FpsRange>.() -> FpsRange? {
    return filtered(
            selector = lowestRangeFps(),
            predicate = { !it.isFixed }
    )
}

/**
 * @return Selector function which selects FPS range with min FPS and fixed rate.
 */
fun lowestFixedFps(): Collection<FpsRange>.() -> FpsRange? {
    return filtered(
            selector = lowestRangeFps(),
            predicate = { it.isFixed }
    )
}

private fun highestRangeFps(): Collection<FpsRange>.() -> FpsRange? = {
    maxWith(CompareFpsRangeByBounds)
}

private fun lowestRangeFps(): Collection<FpsRange>.() -> FpsRange? = {
    minWith(CompareFpsRangeByBounds)
}

private fun Float.toFpsIntRepresentation() = (this * 1000).toInt()