package io.fotoapparat.log;

import android.content.Context;

import java.io.File;

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
	 * @return logger which prints logs to given file.
	 * <p>
	 * Note: if file is not writable, no errors will be produced.
	 */
	public static Logger fileLogger(File file) {
		return new BackgroundThreadLogger(
				new FileLogger(file)
		);
	}

	/**
	 * @return logger which prints logs to file located at {@code context.getExternalFilesDir("logs")}.
	 */
	public static Logger fileLogger(Context context) {
		File logFile = new File(
				context.getExternalFilesDir("logs"),
				"log.txt"
		);

		return new BackgroundThreadLogger(
				new FileLogger(
						logFile
				)
		);
	}

	/**
	 * @return logger which does nothing.
	 */
	public static Logger none() {
		return new DummyLogger();
	}

}
