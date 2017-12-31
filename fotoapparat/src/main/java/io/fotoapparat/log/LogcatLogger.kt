package io.fotoapparat.log

import android.util.Log

/**
 * Uses Logcat to log messages.
 */
internal class LogcatLogger : Logger {

    override fun log(message: String) {
        Log.d("Fotoapparat", message)
    }

}