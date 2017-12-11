package io.fotoapparat.routine.orientation

import io.fotoapparat.hardware.orientation.OrientationSensor

/**
 * Stops orientation monitoring routine.
 */
internal fun OrientationSensor.stopMonitoring() = stop()

