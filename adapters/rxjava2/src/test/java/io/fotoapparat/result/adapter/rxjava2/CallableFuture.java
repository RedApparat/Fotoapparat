package io.fotoapparat.result.adapter.rxjava2;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Simply returns the value of the callable.
 */
public class CallableFuture<T> implements Future<T> {

    private final CountDownLatch latch = new CountDownLatch(1);
    private final Callable<T> callable;

    public CallableFuture(Callable<T> callable) {
        this.callable = callable;
    }

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
        return latch.getCount() == 0;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return callCallable();
    }

    @Override
    public T get(long timeout,
                 @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return callCallable();
    }

    private T callCallable() throws ExecutionException {
        latch.countDown();
        try {
            return callable.call();
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }
}
