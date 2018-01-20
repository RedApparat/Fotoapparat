package io.fotoapparat.routine.camera

import io.fotoapparat.error.CameraErrorCallback
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.execute
import io.fotoapparat.hardware.orientation.Orientation
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.hardware.orientation.OrientationState
import io.fotoapparat.routine.focus.focusOnPoint
import io.fotoapparat.routine.orientation.startOrientationMonitoring

/**
 * Starts the camera from idle.
 */
internal fun Device.bootStart(
        orientationSensor: OrientationSensor,
        mainThreadErrorCallback: CameraErrorCallback
) {
    if (hasSelectedCamera()) {
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

    val cameraDevice = getSelectedCamera().apply {
        open()

        updateCameraConfiguration(
                cameraDevice = this
        )
        setDisplayOrientation(
                orientationState = OrientationState(
                        deviceOrientation = Orientation.Vertical.Portrait,
                        screenOrientation = getScreenOrientation()
                )
        )
    }

    val previewResolution = cameraDevice.getPreviewResolution()

    cameraRenderer.apply {
        setScaleType(
                scaleType = scaleType
        )

        setPreviewResolution(
                resolution = previewResolution
        )
    }

    focusPointSelector?.setFocalPointListener { focalRequest ->
        execute {
            focusOnPoint(focalRequest)
        }
    }

    cameraDevice.run {
        setDisplaySurface(
                preview = cameraRenderer.getPreview()
        )

        startPreview()
    }
}