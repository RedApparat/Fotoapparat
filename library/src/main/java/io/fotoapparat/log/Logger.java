package io.fotoapparat.log;

/**
 * Abstraction which allows to use different implementations of logger.
 */
public interface Logger {

	/**
	 * Logs given message.
	 *
	 * @param message message to log.
	 */
	void log(String message);

}
