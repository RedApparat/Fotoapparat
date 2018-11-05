package io.fotoapparat.routine.focus

import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.metering.FocalRequest
import io.fotoapparat.result.FocusResult
import kotlinx.coroutines.runBlocking

/**
 * Focuses the camera on a particular point.
 */
internal fun Device.focusOnPoint(focalRequest: FocalRequest): FocusResult = runBlocking {
    awaitSelectedCamera()
            .run {
                setFocalPoint(focalRequest)
                autoFocus()
            }
}
