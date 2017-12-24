package io.fotoapparat.routine.camera

import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.getSelectedCameraSafely
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.hardware.shutdownPendingTasks
import io.fotoapparat.routine.orientation.stopMonitoring


/**
 * Stops the camera completely.
 */
internal fun Device.shutDown(
        orientationSensor: OrientationSensor
) {

    shutdownPendingTasks()

    orientationSensor.stopMonitoring()

    val cameraDevice = getSelectedCameraSafely()
    cameraDevice.stop()

    clearSelectedCamera()
}

/**
 * Stops the camera.
 */
internal fun CameraDevice.stop() {
    stopPreview()

    close()
}