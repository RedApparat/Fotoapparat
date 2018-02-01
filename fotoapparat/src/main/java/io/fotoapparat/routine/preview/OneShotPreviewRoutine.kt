package io.fotoapparat.routine.preview

import android.hardware.Camera
import io.fotoapparat.hardware.Device
import kotlinx.coroutines.experimental.runBlocking

/**
 * Sets one shot preview callback - to get single frame from preview stream
 */
internal fun Device.setOneshotPreviewCallback(callback: Camera.PreviewCallback) = runBlocking {
    val camera = awaitSelectedCamera()
    camera.setOneShotPreviewCallback(callback)
}


