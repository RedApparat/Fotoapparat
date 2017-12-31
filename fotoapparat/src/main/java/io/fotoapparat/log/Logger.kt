package io.fotoapparat.log

/**
 * Abstraction which allows to use different implementations of logger.
 *
 *
 * Implementations should not make any assumptions about the calling thread.
 */
interface Logger {

    /**
     * Logs given message.
     *
     * @param message message to log.
     */
    fun log(message: String)

    /**
     * Records a called method.
     */
    fun recordMethod() {
        val lastStacktrace = Exception().stackTrace[2]

        log(
                "${lastStacktrace.className.split('.').last()}: ${lastStacktrace.methodName}"
        )
    }
}
