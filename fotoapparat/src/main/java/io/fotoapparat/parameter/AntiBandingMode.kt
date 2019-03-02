package io.fotoapparat.parameter

/**
 * Anti Banding modes which camera can use.
 */
sealed class AntiBandingMode : Parameter {

    /**
     * Auto adjust. This should be the default.
     */
    object Auto : AntiBandingMode() {
        override fun toString(): String = "AntiBandingMode.Auto"
    }

    /**
     * Anti Banding is set to 50Hz light frequency.
     */
    object HZ50 : AntiBandingMode() {
        override fun toString(): String = "AntiBandingMode.HZ50"
    }

    /**
     * Anti Banding is set to 60Hz light frequency.
     */
    object HZ60 : AntiBandingMode() {
        override fun toString(): String = "AntiBandingMode.HZ60"
    }

    /**
     * Anti Banding is not supported or ignored.
     */
    object None : AntiBandingMode() {
        override fun toString(): String = "AntiBandingMode.None"
    }

}
