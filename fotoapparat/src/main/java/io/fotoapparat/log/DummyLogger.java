package io.fotoapparat.log;

/**
 * Logger which does nothing.
 */
class DummyLogger implements Logger {

    @Override
    public void log(String message) {
        // Do nothing
    }

}
