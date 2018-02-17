package io.fotoapparat.parameter

/**
 * Zoom modes which camera can have.
 */
sealed class Zoom {

    /**
     * The camera can only support one, fixed zoom level.
     */
    object FixedZoom : Zoom()

    /**
     * The camera can only support a variable zoom level between (and including) 0 and [maxZoom] values.
     */
    data class VariableZoom(val maxZoom: Int) : Zoom()

}