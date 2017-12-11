package io.fotoapparat.routine.capability

import io.fotoapparat.capability.Capabilities
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.getSelectedCameraSafely

/**
 * Returns the camera [Capabilities].
 */
internal fun Device.getCapabilities(): Capabilities {

    val cameraDevice = getSelectedCameraSafely()

    return cameraDevice.getCapabilities()
}

