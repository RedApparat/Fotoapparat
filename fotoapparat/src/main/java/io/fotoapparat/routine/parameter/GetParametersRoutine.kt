package io.fotoapparat.routine.parameter

import io.fotoapparat.hardware.Device
import io.fotoapparat.parameter.camera.CameraParameters
import kotlinx.coroutines.runBlocking

/**
 * Returns the current [CameraParameters].
 */
internal fun Device.getCurrentParameters(): CameraParameters = runBlocking {
    val cameraDevice = awaitSelectedCamera()

    cameraDevice.getParameters()
}
