package io.fotoapparat.result;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import io.fotoapparat.result.adapter.Adapter;
import io.fotoapparat.result.transformer.Transformer;

/**
 * Result which might not be readily available at the given moment but will be available in the
 * future.
 */
public class PendingResult<T> {

    private static final Executor TASK_EXECUTOR = Executors.newSingleThreadExecutor();
    private static final Handler MAIN_THREAD_HANDLER = new Handler();

    private final Future<T> future;
    private final Executor executor;

    PendingResult(Future<T> future,
                  Executor executor) {
        this.future = future;
        this.executor = executor;
    }

    /**
     * @return {@link PendingResult} which waits for the result of {@link Future}.
     */
    public static <T> PendingResult<T> fromFuture(@NonNull Future<T> future) {
        return new PendingResult<>(
                future,
                TASK_EXECUTOR
        );
    }

    /**
     * Transforms result from one type to another.
     *
     * @param transformer function which performs transformation of current result type to a new
     *                    type.
     * @return {@link PendingResult} of another type.
     */
    public <R> PendingResult<R> transform(@NonNull final Transformer<T, R> transformer) {
        FutureTask<R> transformTask = new FutureTask<>(new Callable<R>() {
            @Override
            public R call() throws Exception {
                return transformer.transform(
                        future.get()
                );
            }
        });

        executor.execute(transformTask);

        return new PendingResult<>(
                transformTask,
                executor
        );
    }

    /**
     * Blocks current thread until result is available.
     *
     * @return result of execution.
     */
    public T await() throws ExecutionException, InterruptedException {
        return future.get();
    }

    /**
     * Adapts the resulting object to a different type.
     *
     * @param adapter function which performs transforms the current result callback to a new
     *                type.
     * @return result adapted to a new type.
     */
    public <R> R adapt(@NonNull Adapter<T, R> adapter) {
        return adapter.adapt(future);
    }

    /**
     * Notifies given callback as soon as result is available. Callback will always be notified on
     * a main thread.
     */
    public void whenAvailable(@NonNull final Callback<T> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final T result = getResultUnsafe();

                    notifyCallbackOnMainThread(result, callback);
                } catch (RecoverableRuntimeException e) {
                    // Ignore
                }
            }
        });
    }

    /**
     * Alias for {@link #whenAvailable(Callback)}.
     */
    public void whenDone(@NonNull final Callback<T> callback) {
        whenAvailable(callback);
    }

    private void notifyCallbackOnMainThread(final T result,
                                            final Callback<T> callback) {
        MAIN_THREAD_HANDLER.post(new Runnable() {
            @Override
            public void run() {
                callback.onResult(result);
            }
        });
    }

    private T getResultUnsafe() {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Notified when result becomes available.
     */
    public interface Callback<T> {

        /**
         * Called as soon as result is available.
         */
        void onResult(T result);

    }

}
