package io.fotoapparat.routine.parameter

import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.getSelectedCameraSafely
import io.fotoapparat.parameter.camera.CameraParameters

/**
 * Returns the current [CameraParameters].
 */
internal fun Device.getCurrentParameters(): CameraParameters {

    val cameraDevice = getSelectedCameraSafely()

    return cameraDevice.getParameters()
}