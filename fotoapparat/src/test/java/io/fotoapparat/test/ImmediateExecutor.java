package io.fotoapparat.test;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * {@link Executor} which executes operation immediately.
 */
public class ImmediateExecutor implements Executor {

    @Override
    public void execute(@NonNull Runnable command) {
        command.run();
    }

}
