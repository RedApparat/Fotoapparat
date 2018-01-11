package io.fotoapparat.hardware.orientation

/**
 * Phone orientation states.
 */
data class OrientationState(
        /**
         * The current orientation the device is being hold.
         */
        val deviceOrientation: DeviceOrientation,

        /**
         * The current orientation of the screen.
         */
        val screenOrientation: ScreenOrientation
)