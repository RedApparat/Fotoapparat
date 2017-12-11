package io.fotoapparat.exception

/**
 * Thrown when it is not possible to decode bitmap from byte array.
 */
class UnableToDecodeBitmapException : RecoverableRuntimeException("Unable to decode bitmap")