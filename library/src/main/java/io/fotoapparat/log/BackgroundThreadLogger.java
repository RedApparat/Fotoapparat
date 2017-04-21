package io.fotoapparat.log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Wrapper around another {@link Logger} which moves execution to another thread.
 */
class BackgroundThreadLogger implements Logger {

    private static final Executor LOGGER_EXECUTOR = Executors.newSingleThreadExecutor();

    private final Logger logger;

    public BackgroundThreadLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(final String message) {
        LOGGER_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                logger.log(message);
            }
        });
    }

}
