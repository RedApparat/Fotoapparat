package io.fotoapparat.result.adapter.rxjava3;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.rxjava3.observers.TestObserver;

public class SingleAdapterTest {

    private final TestObserver<String> observer = new TestObserver<>();

    @Test
    public void completed() {
        // Given
        Future<String> future = new CallableFuture<>(() -> "Hello");

        // When
        SingleAdapter.<String>toSingle()
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
        SingleAdapter.<String>toSingle()
                .invoke(future)
                .subscribe(observer);

        // Then
        observer.assertNoValues();
        observer.assertError(ExecutionException.class);
    }

}