package io.fotoapparat.hardware.orientation

import android.content.Context
import io.fotoapparat.hardware.Device


/**
 * Monitors orientation of the device.
 */
internal open class OrientationSensor(
        private val rotationListener: RotationListener,
        private val device: Device
) {

    constructor(context: Context,
                device: Device
    ) : this(
            RotationListener(context),
            device
    )

    private val onOrientationChanged = {
        device.getScreenRotation().let {
            if (it != lastKnownRotation) {
                listener(it)
                lastKnownRotation = it
            }
        }
    }

    init {
        rotationListener.orientationChanged = onOrientationChanged
    }

    private lateinit var listener: (Int) -> Unit
    private var lastKnownRotation: Int = 0

    /**
     * Starts monitoring device's orientation.
     */
    open fun start(listener: (Int) -> Unit) {
        this.listener = listener
        rotationListener.enable()
    }

    /**
     * Stops monitoring device's orientation.
     */
    open fun stop() {
        rotationListener.disable()
    }

}