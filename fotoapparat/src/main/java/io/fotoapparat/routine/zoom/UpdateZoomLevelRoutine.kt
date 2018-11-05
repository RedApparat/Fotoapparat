package io.fotoapparat.routine.zoom

import androidx.annotation.FloatRange
import io.fotoapparat.exception.LevelOutOfRangeException
import io.fotoapparat.hardware.Device
import io.fotoapparat.parameter.Zoom
import kotlinx.coroutines.runBlocking

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

    if (cameraDevice.getCapabilities().zoom is Zoom.VariableZoom) {
        cameraDevice.setZoom(zoomLevel)
    }
}

private fun Float.ensureInBounds() {
    if (this !in 0f..1f) {
        throw LevelOutOfRangeException(this)
    }
}
