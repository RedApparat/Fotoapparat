package io.fotoapparat.log

import android.content.Context
import java.io.File

/**
 * @return logger which does nothing.
 */
fun none(): Logger = DummyLogger()

/**
 * @return logger which prints logs to Android Logcat.
 */
fun logcat(): Logger = LogcatLogger()

/**
 * @return logger which prints logs to given file.
 *
 *
 * Note: if file is not writable, no errors will be produced.
 */
fun fileLogger(file: File): Logger = BackgroundThreadLogger(FileLogger(file))

/**
 * @return logger which prints logs to file located at `context.getExternalFilesDir("logs")`.
 */
fun fileLogger(context: Context): Logger {
    val logFile = File(
            context.getExternalFilesDir("logs"),
            "log.txt"
    )

    return fileLogger(logFile)
}

/**
 * @return logger which combine multiple other loggers together.
 */
fun loggers(vararg loggers: Logger): Logger = CompositeLogger(loggers.toList())


