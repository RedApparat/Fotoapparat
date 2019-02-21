package io.fotoapparat.characteristic

/**
 * The camera position relatively to the screen of the device.
 */
sealed class LensPosition : Characteristic {

    /**
     * The back camera.
     */
    object Back : LensPosition() {
        override fun toString(): String = "LensPosition.Back"
    }

    /**
     * The front camera.
     */
    object Front : LensPosition() {
        override fun toString(): String = "LensPosition.Front"
    }

    /**
     * An external camera.
     */
    object External : LensPosition() {
        override fun toString(): String = "LensPosition.External"
    }

}