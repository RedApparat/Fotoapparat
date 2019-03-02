package io.fotoapparat.parameter

/**
 * Focus modes which camera can use.
 */
sealed class FocusMode : Parameter {

    /**
     * Focus is not adjustable. It is always used by devices which do not support auto-focus.
     */
    object Fixed : FocusMode() {
        override fun toString(): String = "FocusMode.Fixed"
    }

    /**
     * Camera is focused at infinity.
     */
    object Infinity : FocusMode() {
        override fun toString(): String = "FocusMode.Infinity"
    }

    /**
     * Macro focus mode.
     */
    object Macro : FocusMode() {
        override fun toString(): String = "FocusMode.Macro"
    }

    /**
     * Auto focus. Camera is trying to focus automatically when manually requested.
     */
    object Auto : FocusMode() {
        override fun toString(): String = "FocusMode.Auto"
    }

    /**
     * Camera is constantly trying to stay in focus.
     *
     * The speed of focus change is more aggressive than [ContinuousFocusVideo].
     */
    object ContinuousFocusPicture : FocusMode() {
        override fun toString(): String = "FocusMode.ContinuousFocusPicture"
    }

    /**
     * Camera is constantly trying to stay in focus.
     *
     * The speed of focus change is smoother than [ContinuousFocusPicture].
     */
    object ContinuousFocusVideo : FocusMode() {
        override fun toString(): String = "FocusMode.ContinuousFocusVideo"
    }

    /**
     * The camera device will produce images with an extended depth of field.
     */
    object Edof : FocusMode() {
        override fun toString(): String = "FocusMode.Edof"
    }

}