package io.fotoapparat.routine.capability

import io.fotoapparat.capability.Capabilities
import io.fotoapparat.hardware.Device
import kotlinx.coroutines.runBlocking

/**
 * Returns the camera [Capabilities].
 */
internal fun Device.getCapabilities(): Capabilities = runBlocking {
    val cameraDevice = awaitSelectedCamera()

    cameraDevice.getCapabilities()
}

