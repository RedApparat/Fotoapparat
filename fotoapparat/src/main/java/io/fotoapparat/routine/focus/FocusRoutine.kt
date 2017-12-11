package io.fotoapparat.routine.focus

import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.getSelectedCameraSafely
import io.fotoapparat.result.FocusResult

/**
 * Focuses the camera.
 */
internal fun Device.focus(): FocusResult {
    val cameraDevice = getSelectedCameraSafely()

    return cameraDevice.autoFocus()
}