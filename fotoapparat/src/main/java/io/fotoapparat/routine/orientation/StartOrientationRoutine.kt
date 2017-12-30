package io.fotoapparat.routine.orientation

import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.execute
import io.fotoapparat.hardware.orientation.OrientationSensor

/**
 * Starts orientation monitoring routine.
 */
internal fun Device.startOrientationMonitoring(
        orientationSensor: OrientationSensor
) {
    orientationSensor.start { degrees ->
        execute {
            val cameraDevice = getSelectedCamera()
            cameraDevice.setDisplayOrientation(degrees)
        }
    }
}
