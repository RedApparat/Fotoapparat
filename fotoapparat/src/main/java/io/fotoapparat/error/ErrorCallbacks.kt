package io.fotoapparat.error

import android.os.Looper
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.executeMainThread

/**
 * @return CameraErrorCallback which will always move execution to the main thread.
 */
fun ((CameraException) -> Unit).onMainThread(): (CameraException) -> Unit = { cameraException ->
    if (Looper.myLooper() == Looper.getMainLooper()) {
        this(cameraException)
    } else {
        executeMainThread { this(cameraException) }
    }
}