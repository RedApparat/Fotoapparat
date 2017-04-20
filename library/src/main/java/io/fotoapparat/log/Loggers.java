package io.fotoapparat.log;

/**
 * Built-in implementations of {@link Logger}.
 */
public class Loggers {

	/**
	 * @return logger which prints logs to Android Logcat.
	 */
	public static Logger logcat() {
		return new LogcatLogger();
	}

	/**
	 * @return logger which does nothing.
	 */
	public static Logger none() {
		return new DummyLogger();
	}

}
