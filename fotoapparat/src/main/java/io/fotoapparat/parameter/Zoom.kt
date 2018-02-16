package io.fotoapparat.parameter

/**
 * Zoom modes which camera can use.
 */
sealed class Zoom {

    object FixedZoom : Zoom()

    data class VariableZoom(val minZoom: Int, val maxZoom: Int) : Zoom()

}