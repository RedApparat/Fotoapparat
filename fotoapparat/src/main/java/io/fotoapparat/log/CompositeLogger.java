package io.fotoapparat.log;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Logger which delegates messages to multiple other loggers.
 */
class CompositeLogger implements Logger {

    private final List<Logger> loggers;

    CompositeLogger(@NonNull List<Logger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void log(String message) {
        for (Logger logger : loggers) {
            logger.log(message);
        }
    }

}
