package io.fotoapparat.result;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import io.fotoapparat.result.transformer.Transformer;

/**
 * Result which might not be readily available at the given moment but will be available in the
 * future.
 */
public class PendingResult<T> {

	private static final Executor TASK_EXECUTOR = Executors.newSingleThreadExecutor();

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
	public static <T> PendingResult<T> fromFuture(Future<T> future) {
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
	public <R> PendingResult<R> transform(final Transformer<T, R> transformer) {
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

}
