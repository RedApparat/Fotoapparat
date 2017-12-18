package io.fotoapparat.parameter;

/**
 * Anti Banding modes which camera can use.
 */
public enum AntiBandingMode {

    /**
     * Auto adjust. This should be the default.
     */
    AUTO,

    /**
     * Anti Banding is set to 50Hz light frequency.
     */
    HZ50,

    /**
     * Anti Banding is set to 60Hz light frequency.
     */
    HZ60,

    /**
     * Anti Banding is not supported or ignored.
     */
    NONE

}
