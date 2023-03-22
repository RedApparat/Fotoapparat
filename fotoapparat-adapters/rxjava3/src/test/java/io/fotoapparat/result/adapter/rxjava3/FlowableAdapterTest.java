package io.fotoapparat.result.adapter.rxjava3;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.rxjava3.subscribers.TestSubscriber;

public class FlowableAdapterTest {

    private final TestSubscriber<String> observer = new TestSubscriber<>();

    @Test
    public void completed() {
        // Given
        Future<String> future = new CallableFuture<>(() -> "Hello");

        // When
        FlowableAdapter.<String>toFlowable()
                .invoke(future)
                .subscribe(observer);

        // Then
        observer.assertValue("Hello");
        observer.assertNoErrors();
    }

    @Test
    public void error() {
        // Given
        Future<String> future = new CallableFuture<>(() -> {
            throw new RuntimeException("What a failure");
        });

        // When
        FlowableAdapter.<String>toFlowable()
                .invoke(future)
                .subscribe(observer);

        // Then
        observer.assertNoValues();
        observer.assertError(ExecutionException.class);
    }

}