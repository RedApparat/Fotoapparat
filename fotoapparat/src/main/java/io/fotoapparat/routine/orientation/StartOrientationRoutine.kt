package io.fotoapparat.routine.orientation

import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.execute
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.hardware.orientation.OrientationState

/**
 * Starts orientation monitoring routine.
 */
internal fun Device.startOrientationMonitoring(
        orientationSensor: OrientationSensor
) {
    orientationSensor.start { orientationState: OrientationState ->
        execute {
            val cameraDevice = getSelectedCamera()
            cameraDevice.setDisplayOrientation(orientationState)
        }
    }
}
