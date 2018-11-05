package io.fotoapparat.routine.focus

import io.fotoapparat.hardware.Device
import io.fotoapparat.result.FocusResult
import kotlinx.coroutines.runBlocking

/**
 * Focuses the camera.
 */
internal fun Device.focus(): FocusResult = runBlocking {
    val cameraDevice = awaitSelectedCamera()

    cameraDevice.autoFocus()
}
