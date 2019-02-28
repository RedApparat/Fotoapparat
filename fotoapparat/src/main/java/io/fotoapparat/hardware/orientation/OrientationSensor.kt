package io.fotoapparat.hardware.orientation

import android.content.Context
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.orientation.Orientation.Vertical.Portrait

/**
 * Monitors orientation of the device.
 */
internal open class OrientationSensor(
        private val rotationListener: RotationListener,
        private val device: Device
) {

    private val onOrientationChanged: (DeviceRotationDegrees) -> Unit = { deviceRotation ->
        deviceRotation.toClosestRightAngle()
                .toOrientation()
                .let {
                    val screenOrientation = device.getScreenOrientation()
                    val deviceOrientation = it
                    if(deviceOrientation != lastKnownDeviceOrientation
                            || screenOrientation != lastKnowScreenOrientation){
                        val state = OrientationState(
                                deviceOrientation = deviceOrientation,
                                screenOrientation = screenOrientation
                        )
                        lastKnowScreenOrientation = state.screenOrientation
                        lastKnownDeviceOrientation = state.deviceOrientation
                        listener(state)
                    }
                }
    }

    private lateinit var listener: (OrientationState) -> Unit
    private var lastKnownDeviceOrientation: Orientation = Portrait
    private var lastKnowScreenOrientation: Orientation = Portrait

    constructor(
            context: Context,
            device: Device
    ) : this(
            RotationListener(context),
            device
    )

    init {
        rotationListener.orientationChanged = onOrientationChanged
    }

    /**
     * Starts monitoring device's orientation state.
     */
    open fun start(listener: (OrientationState) -> Unit) {
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
