package io.fotoapparat.exception.camera

/**
 * Thrown to indicate that the device has no camera for the desired lens position(s).
 */
class UnsupportedLensException : CameraException("Device has no camera for the desired lens position(s).")