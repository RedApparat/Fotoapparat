package io.fotoapparat.error

import io.fotoapparat.exception.camera.CameraException

/**
 * Notified when an camera error happens within Fotoapparat.
 *
 * This method is always called from the main thread.
 */
interface CameraErrorListener {

    /**
     * Notified when a camera error happens within Fotoapparat.
     */
    fun onError(e: CameraException)
}