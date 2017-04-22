package io.fotoapparat.parameter;

/**
 * Flash modes which camera can use.
 */
public enum Flash {

    /**
     * Camera flash will not fire.
     */
    OFF,

    /**
     * Camera flash will always fire regardless light conditions.
     */
    ON,

    /**
     * Camera flash will fire only in low light conditions.
     */
    AUTO,

    /**
     * If deemed necessary by the camera device, a red eye reduction flash will fire during the
     * precapture sequence in low light conditions.
     */
    AUTO_RED_EYE,

    /**
     * Transition flash to continuously on.
     */
    TORCH

}
