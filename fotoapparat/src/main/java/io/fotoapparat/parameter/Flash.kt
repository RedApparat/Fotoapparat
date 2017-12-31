package io.fotoapparat.parameter

/**
 * Flash modes which camera can use.
 */
sealed class Flash : Parameter {

    /**
     * Camera flash will not fire.
     */
    object Off : Flash()

    /**
     * Camera flash will always fire regardless light conditions.
     */
    object On : Flash()

    /**
     * Camera flash will fire only in low light conditions.
     */
    object Auto : Flash()

    /**
     * If deemed necessary by the camera device, a red eye reduction flash will fire during the
     * precapture sequence in low light conditions.
     */
    object AutoRedEye : Flash()

    /**
     * Transition flash to continuously on.
     */
    object Torch : Flash()

}