package io.fotoapparat.test;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.Arrays.asList;

/**
 * Test related utilities.
 */
public class TestUtils {

    /**
     * Immediately performs given {@link FutureTask} and returns it's result.
     *
     * @return result of given {@link FutureTask}
     */
    public static <T> T resultOf(FutureTask<T> task) throws ExecutionException, InterruptedException {
        task.run();
        return task.get();
    }

    /**
     * @return {@link Future} which is immediately ready to provide given value.
     */
    public static <T> Future<T> immediateFuture(final T value) {
        return new Future<T>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public T get() throws InterruptedException, ExecutionException {
                return value;
            }

            @Override
            public T get(long timeout,
                         @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return value;
            }
        };
    }

    public static <T> Set<T> asSet(T... items) {
        return new HashSet<>(asList(items));
    }

}
