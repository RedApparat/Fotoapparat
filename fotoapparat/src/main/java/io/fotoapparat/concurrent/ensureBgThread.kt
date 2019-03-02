package io.fotoapparat.concurrent

import android.os.Looper

/**
 * Throws [IllegalThreadStateException] if called from main thread.
 */
fun ensureBackgroundThread() {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        throw IllegalThreadStateException("Operation should not run from main thread.")
    }
}