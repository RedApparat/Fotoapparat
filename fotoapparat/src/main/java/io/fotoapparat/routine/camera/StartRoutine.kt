package io.fotoapparat.routine.camera

import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.routine.orientation.startOrientationMonitoring

/**
 * Starts the camera from idle.
 */
internal fun Device.bootStart(
        orientationSensor: OrientationSensor,
        mainThreadErrorCallback: (CameraException) -> Unit
) {
    getSelectedCamera()?.let {
        throw IllegalStateException("Camera has already started!")
    }

    try {
        start()
        startOrientationMonitoring(
                orientationSensor = orientationSensor
        )
    } catch (e: CameraException) {
        mainThreadErrorCallback(e)
    }
}

/**
 * Starts the camera.
 */
internal fun Device.start() {
    selectCamera()

    val cameraDevice = getSelectedCamera()
            ?: throw CameraException("Device has no camera for the desired lens position(s).")

    cameraDevice.open()

    updateCameraConfiguration(
            cameraDevice = cameraDevice
    )

    cameraDevice.setDisplayOrientation(
            degrees = getScreenRotation()
    )

    cameraRenderer.setScaleType(
            scaleType = scaleType
    )
    cameraRenderer.setPreviewResolution(
            resolution = cameraDevice.getPreviewResolution()
    )
    cameraDevice.setDisplaySurface(
            displaySurface = cameraRenderer.getSurfaceTexture()
    )
    cameraDevice.startPreview()
}