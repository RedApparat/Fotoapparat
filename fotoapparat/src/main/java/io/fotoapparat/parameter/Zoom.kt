package io.fotoapparat.parameter

/**
 * Zoom modes which camera can have.
 */
sealed class Zoom {

    /**
     * The camera can only support one, fixed zoom level.
     */
    object FixedZoom : Zoom() {
        override fun toString(): String = "Zoom.FixedZoom"
    }

    /**
     * The camera can only support a variable zoom level between (and including) 0 and [maxZoom] values.
     * [zoomRatios] is a list of all zoom values. Ratios are in 1/100 increments. For example, zoom of
     * 2.7x is returned as 270. The number of elements is [maxZoom] + 1. List is sorted from small to
     * large. First element is always 100. The last element is the zoom ratio of the maximum zoom value.
     */
    data class VariableZoom(val maxZoom: Int, val zoomRatios: List<Int>) : Zoom() {
        override fun toString(): String = "Zoom.VariableZoom(maxZoom=$maxZoom, zoomRatios=$zoomRatios)"
    }

}