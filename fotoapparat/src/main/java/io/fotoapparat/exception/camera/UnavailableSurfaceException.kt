package io.fotoapparat.exception.camera

/**
 * Thrown when the preview surface didn't become available.
 */
class UnavailableSurfaceException : CameraException(
        "No preview surface became available before CameraView got detached from window. " +
                "Camera didn't start. You may ignore this exception."
)