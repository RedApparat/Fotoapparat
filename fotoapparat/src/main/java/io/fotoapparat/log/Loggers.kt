package io.fotoapparat.log

/**
 * @return logger which does nothing.
 */
fun none(): Logger = DummyLogger()

/**
 * @return logger which prints logs to Android Logcat.
 */
fun logcat(): Logger = LogcatLogger()

/**
 * @return logger which combine multiple other loggers together.
 */
fun loggers(vararg loggers: Logger): Logger = CompositeLogger(loggers.toList())

