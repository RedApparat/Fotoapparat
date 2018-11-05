package io.fotoapparat.log

/**
 * Logger which delegates messages to multiple other loggers.
 */
internal class CompositeLogger(private val loggers: List<Logger>) : Logger {

    override fun log(message: String) {
        loggers.forEach { it.log(message) }
    }

}
