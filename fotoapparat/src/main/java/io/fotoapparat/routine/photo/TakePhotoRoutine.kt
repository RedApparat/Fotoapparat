package io.fotoapparat.routine.photo

import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.getSelectedCameraSafely
import io.fotoapparat.result.Photo

/**
 * Takes a photo.
 */
internal fun Device.takePhoto(): Photo {

    val cameraDevice = getSelectedCameraSafely()

    return cameraDevice.takePhoto().also {
        cameraDevice.startPreviewSafely()
    }
}

private fun CameraDevice.startPreviewSafely() {
    try {
        startPreview()
    } catch (e: CameraException) {
        //Nothing
    }
}
