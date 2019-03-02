package io.fotoapparat.parameter

/**
 * Flash modes which camera can use.
 */
sealed class Flash : Parameter {

    /**
     * Camera flash will not fire.
     */
    object Off : Flash() {
        override fun toString(): String = "Flash.Off"
    }

    /**
     * Camera flash will always fire regardless light conditions.
     */
    object On : Flash() {
        override fun toString(): String = "Flash.On"
    }

    /**
     * Camera flash will fire only in low light conditions.
     */
    object Auto : Flash() {
        override fun toString(): String = "Flash.Auto"
    }

    /**
     * If deemed necessary by the camera device, a red eye reduction flash will fire during the
     * precapture sequence in low light conditions.
     */
    object AutoRedEye : Flash() {
        override fun toString(): String = "Flash.AutoRedEye"
    }

    /**
     * Transition flash to continuously on.
     */
    object Torch : Flash() {
        override fun toString(): String = "Flash.Torch"
    }

}