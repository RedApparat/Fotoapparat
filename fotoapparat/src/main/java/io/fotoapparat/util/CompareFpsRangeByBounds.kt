package io.fotoapparat.util

import io.fotoapparat.parameter.FpsRange
import java.util.*

/**
 * Comparator based on bounds check. Lower bound has higher priority.
 */
internal object CompareFpsRangeByBounds : Comparator<FpsRange> {

    override fun compare(fpsRange1: FpsRange, fpsRange2: FpsRange): Int {
        return fpsRange1.min.compareTo(fpsRange2.min).run {
            when (this) {
                0 -> fpsRange1.max.compareTo(fpsRange2.max)
                else -> this
            }
        }
    }
}