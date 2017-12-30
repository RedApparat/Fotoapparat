package io.fotoapparat.routine.zoom

import android.support.annotation.FloatRange
import io.fotoapparat.exception.LevelOutOfRangeException
import io.fotoapparat.hardware.Device
import kotlinx.coroutines.experimental.runBlocking


/**
 * Updates zoom level of the camera. If zoom is not supported - does nothing.
 *
 * @param zoomLevel zoom level of the camera. Value between 0 and 1.
 */
internal fun Device.updateZoomLevel(
        @FloatRange(from = 0.0, to = 1.0) zoomLevel: Float
) = runBlocking {
    zoomLevel.ensureInBounds()
    val cameraDevice = awaitSelectedCamera()

    if (cameraDevice.getCapabilities().canZoom) {
        cameraDevice.setZoom(zoomLevel)
    }
}

private fun Float.ensureInBounds() {
    if (this < 0f || this > 1f) {
        throw LevelOutOfRangeException(this)
    }
}