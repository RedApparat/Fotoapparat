package io.fotoapparat.result.adapter.rxjava3;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.rxjava3.observers.TestObserver;

public class CompletableAdapterTest {

    private final TestObserver<String> observer = new TestObserver<>();

    @Test
    public void completed() {
        // Given
        Future<String> future = new CallableFuture<>(() -> "Hello");

        // When
        CompletableAdapter.<String>toCompletable()
                .invoke(future)
                .subscribe(observer);

        // Then
        observer.assertNoValues();
        observer.assertNoErrors();
    }

    @Test
    public void error() throws Exception {
        // Given
        Future<String> future = new CallableFuture<>(() -> {
            throw new RuntimeException("What a failure");
        });

        // When
        CompletableAdapter.<String>toCompletable()
                .invoke(future)
                .subscribe(observer);

        // Then
        observer.assertNoValues();
        observer.assertError(ExecutionException.class);
    }

}