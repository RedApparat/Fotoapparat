package io.fotoapparat.routine.photo

import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.result.Photo
import kotlinx.coroutines.experimental.runBlocking

/**
 * Takes a photo.
 */
internal fun Device.takePhoto(): Photo = runBlocking {
    val cameraDevice = awaitSelectedCamera()

    cameraDevice.takePhoto().also {
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
