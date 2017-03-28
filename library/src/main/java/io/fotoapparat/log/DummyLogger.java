package io.fotoapparat.log;

/**
 * Logger which does nothing.
 */
public class DummyLogger implements Logger {

	@Override
	public void log(String message) {
		// Do nothing
	}

}
