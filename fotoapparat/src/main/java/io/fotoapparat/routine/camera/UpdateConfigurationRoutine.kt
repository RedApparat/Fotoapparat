package io.fotoapparat.routine.camera

import io.fotoapparat.configuration.Configuration
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.getSelectedCameraSafely


/**
 * Updates [Device] configuration.
 */
internal fun Device.updateDeviceConfiguration(configuration: Configuration) {
    val cameraDevice = getSelectedCameraSafely()

    updateConfiguration(configuration)

    updateCameraConfiguration(cameraDevice = cameraDevice)
}

/**
 * Updates [CameraDevice] parameters.
 */
internal fun Device.updateCameraConfiguration(
        cameraDevice: CameraDevice
) {
    val cameraParameters = getCameraParameters(cameraDevice)
    val frameProcessor = getFrameProcessor()

    cameraDevice.updateParameters(
            cameraParameters = cameraParameters
    )

    cameraDevice.updateFrameProcessor(
            frameProcessor = frameProcessor
    )
}