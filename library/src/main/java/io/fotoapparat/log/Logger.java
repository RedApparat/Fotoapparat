package io.fotoapparat.log;

/**
 * Abstraction which allows to use different implementations of logger.
 * <p>
 * Implementations should not make any assumptions about the calling thread.
 */
public interface Logger {

    /**
     * Logs given message.
     *
     * @param message message to log.
     */
    void log(String message);

}
