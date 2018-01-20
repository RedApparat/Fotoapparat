package io.fotoapparat.concurrent

import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Executes camera related operations using a dedicated thread and provides a control over the queue
 * of those operations.
 *
 * This class should be accessed only from a one thread at a time.
 */
class CameraExecutor(
        private val executor: ExecutorService = Executors.newSingleThreadExecutor()
) {

    private val cancellableTasksQueue = LinkedList<Future<*>>()

    /**
     * Adds operation to the serial execution queue.
     */
    fun <T> execute(operation: Operation<T>): Future<T> {
        val future = executor.submit(Callable {
            operation.function()
        })

        if (operation.cancellable) {
            cancellableTasksQueue += future
        }

        cleanUpCancelledTasks()

        return future
    }

    private fun cleanUpCancelledTasks() {
        cancellableTasksQueue.removeAll {
            !it.isPending
        }
    }

    /**
     * Cancels all cancellable tasks in the queue. Non-cancellable tasks would still be executed.
     *
     * After this operation is completed the executor is still in a valid state and can be used
     * further.
     */
    fun cancelTasks() {
        cancellableTasksQueue
                .filter { it.isPending }
                .forEach {
                    it.cancel(true)
                }
        cancellableTasksQueue.clear()
    }

    private val Future<*>.isPending get() = !isCancelled && !isDone

    /**
     * Operation to be executed.
     */
    data class Operation<out T>(

            /**
             * `true` if operation could be cancelled. `false` if operation can not be cancelled.
             */
            val cancellable: Boolean = false,

            /**
             * Body of the operation.
             */
            val function: () -> T

    )

}