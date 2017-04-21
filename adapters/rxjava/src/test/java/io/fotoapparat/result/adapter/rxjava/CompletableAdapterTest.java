package io.fotoapparat.result.adapter.rxjava;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.observers.TestSubscriber;

public class CompletableAdapterTest {

    private TestSubscriber<Object> subscriber = new TestSubscriber<>();

    @Test
    public void completed() throws Exception {
        // Given
        Future<String> future = new CallableFuture<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello";
            }
        });

        // When
        CompletableAdapter.<String>toCompletable()
                .adapt(future)
                .subscribe(subscriber);

        // Then
        subscriber.assertNoValues();
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

    @Test
    public void error() throws Exception {
        // Given
        Future<String> future = new CallableFuture<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new RuntimeException("What a failure");
            }
        });

        // When
        CompletableAdapter.<String>toCompletable()
                .adapt(future)
                .subscribe(subscriber);

        // Then
        subscriber.assertNoValues();
        subscriber.assertError(ExecutionException.class);
    }

}