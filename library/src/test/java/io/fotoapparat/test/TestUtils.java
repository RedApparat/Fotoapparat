package io.fotoapparat.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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

}
