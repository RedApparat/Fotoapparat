package io.fotoapparat.routine.camera

import io.fotoapparat.configuration.Configuration
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import kotlinx.coroutines.runBlocking

/**
 * Updates [Device] configuration.
 */
internal fun Device.updateDeviceConfiguration(newConfiguration: Configuration) {
    val cameraDevice = getSelectedCamera()

    updateConfiguration(newConfiguration)

    updateCameraConfiguration(cameraDevice = cameraDevice)
}

/**
 * Updates [CameraDevice] parameters.
 */
internal fun Device.updateCameraConfiguration(
        cameraDevice: CameraDevice
) = runBlocking {
    val cameraParameters = getCameraParameters(cameraDevice)
    val frameProcessor = getFrameProcessor()

    cameraDevice.updateParameters(
            cameraParameters = cameraParameters
    )

    cameraDevice.updateFrameProcessor(
            frameProcessor = frameProcessor
    )
}
