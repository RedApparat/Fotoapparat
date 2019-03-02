package io.fotoapparat.routine.camera

import io.fotoapparat.concurrent.CameraExecutor.Operation
import io.fotoapparat.error.CameraErrorCallback
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.routine.focus.focusOnPoint
import io.fotoapparat.routine.orientation.startOrientationMonitoring
import java.io.IOException

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
        start(
                orientationSensor = orientationSensor
        )
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
internal fun Device.start(orientationSensor: OrientationSensor) {
    selectCamera()

    val cameraDevice = getSelectedCamera().apply {
        open()

        updateCameraConfiguration(
                cameraDevice = this
        )
        setDisplayOrientation(orientationSensor.lastKnownOrientationState)
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
        executor.execute(Operation(cancellable = true) {
            focusOnPoint(focalRequest)
        })
    }

    with(cameraDevice) {
        try {
            setDisplaySurface(
                    preview = cameraRenderer.getPreview()
            )

            startPreview()
        } catch (e: IOException) {
            logger.log("Can't start preview because of the exception: $e")
        }
    }
}