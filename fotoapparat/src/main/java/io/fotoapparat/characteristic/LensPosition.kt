package io.fotoapparat.characteristic

/**
 * The camera position relatively to the screen of the device.
 */
sealed class LensPosition : Characteristic {

    /**
     * The back camera.
     */
    object Back : LensPosition()

    /**
     * The front camera.
     */
    object Front : LensPosition()

    /**
     * An external camera.
     */
    object External : LensPosition()

}