package io.fotoapparat.parameter;

/**
 * Focus modes which camera can use.
 */
public enum FocusMode {

    /**
     * Focus is not adjustable. It is always used by devices which do not support auto-focus.
     */
    FIXED,

    /**
     * Camera is focused at infinity.
     */
    INFINITY,

    /**
     * Macro focus mode.
     */
    MACRO,

    /**
     * Auto focus. Camera is trying to focus automatically when manually requested.
     */
    AUTO,

    /**
     * Camera is constantly trying to stay in focus.
     */
    CONTINUOUS_FOCUS,

    /**
     * The camera device will produce images with an extended depth of field.
     */
    EDOF

}
