package io.fotoapparat.selector

import androidx.annotation.FloatRange

/**
 * @param selector Input selector
 * @param tolerance Aspect ratio tolerance (0.0 - 1.0)
 *
 * @return Selector function which selects some size around standard aspect ratio (4:3), with given tolerance.
 */
@JvmOverloads
fun standardRatio(
        selector: ResolutionSelector,
        @FloatRange(from = 0.0, to = 1.0) tolerance: Double = 0.0
): ResolutionSelector = aspectRatio(
        aspectRatio = 4f / 3f,
        selector = selector,
        tolerance = tolerance
)

/**
 * @param selector Input sizes, selected by provided selector function
 * @param tolerance Aspect ratio tolerance in range of 0.0 (0%) to 1.0 (100%)
 *
 * @return Selector function which selects some size around wide aspect ratio (16:9), with given tolerance.
 */
@JvmOverloads
fun wideRatio(
        selector: ResolutionSelector,
        @FloatRange(from = 0.0, to = 1.0) tolerance: Double = 0.0
): ResolutionSelector = aspectRatio(
        aspectRatio = 16f / 9f,
        selector = selector,
        tolerance = tolerance
)

/**
 * Select sizes with desired aspect ratio. This selector can
 * select sizes that differ slightly from ideal aspect ratio.
 *
 * @param aspectRatio Desired aspect ratio
 * @param tolerance Aspect ratio tolerance, as 0.0 - 1.0, where 0 is exact aspect ratio and 1 allows 100% deviation
 *
 * @return Selector function which selects some size around given aspect ratio.
 */
@JvmOverloads
fun aspectRatio(
        aspectRatio: Float,
        selector: ResolutionSelector,
        @FloatRange(from = 0.0, to = 1.0) tolerance: Double = 0.0
): ResolutionSelector {
    if (tolerance !in 0.0..1.0) {
        throw IllegalArgumentException("Tolerance must be between 0.0 and 1.0.")
    }

    val calculatedTolerance = aspectRatio * tolerance + ASPECT_RATIO_EPSILON

    return filtered(selector) {
        Math.abs(aspectRatio - it.aspectRatio) <= calculatedTolerance
    }
}

private const val ASPECT_RATIO_EPSILON = 1e-4

