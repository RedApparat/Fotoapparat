package io.fotoapparat.log;

import android.util.Log;

/**
 * Uses Logcat to log messages.
 */
class LogcatLogger implements Logger {

    @Override
    public void log(String message) {
        Log.d("Fotoapparat", message);
    }

}
