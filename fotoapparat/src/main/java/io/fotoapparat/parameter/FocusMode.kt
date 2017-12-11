package io.fotoapparat.parameter

/**
 * Focus modes which camera can use.
 */
sealed class FocusMode : Parameter {

    /**
     * Focus is not adjustable. It is always used by devices which do not support auto-focus.
     */
    object Fixed : FocusMode()

    /**
     * Camera is focused at infinity.
     */
    object Infinity : FocusMode()

    /**
     * Macro focus mode.
     */
    object Macro : FocusMode()

    /**
     * Auto focus. Camera is trying to focus automatically when manually requested.
     */
    object Auto : FocusMode()

    /**
     * Camera is constantly trying to stay in focus.
     *
     * The speed of focus change is more aggressive than [ContinuousFocusVideo].
     */
    object ContinuousFocusPicture : FocusMode()

    /**
     * Camera is constantly trying to stay in focus.
     *
     * The speed of focus change is smoother than [ContinuousFocusPicture].
     */
    object ContinuousFocusVideo : FocusMode()

    /**
     * The camera device will produce images with an extended depth of field.
     */
    object Edof : FocusMode()

}