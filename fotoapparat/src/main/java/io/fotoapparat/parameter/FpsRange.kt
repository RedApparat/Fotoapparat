package io.fotoapparat.parameter

/**
 * Frame rate (FPS) range which camera's preview can display.
 *
 * The values are multiplied by 1000 and represented in integers.
 * For example, if frame rate is 26.623 frames per second, the value is 26623.
 */
data class FpsRange(
        /**
         * The min fps the preview can support.
         * e.g. 26623
         */
        val min: Int,

        /**
         * The max fps the preview can support.
         * e.g. 30000
         */
        val max: Int
) : Parameter, ClosedRange<Int> by IntRange(
        start = min,
        endInclusive = max
) {

    /**
     * `true` if the current range is fixed (min == max).
     */
    val isFixed get() = max == min
}