package io.fotoapparat.result.adapter.rxjava2

import java.util.concurrent.*

/**
 * Simply returns the value of the callable.
 */
class CallableFuture<T>(private val callable: Callable<T>) : Future<T> {

    private val latch = CountDownLatch(1)

    override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        return false
    }

    override fun isCancelled(): Boolean {
        return false
    }

    override fun isDone(): Boolean {
        return latch.count == 0L
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    override fun get(): T {
        return callCallable()
    }

    @Throws(InterruptedException::class, ExecutionException::class, TimeoutException::class)
    override fun get(timeout: Long, unit: TimeUnit): T {
        return callCallable()
    }

    @Throws(ExecutionException::class)
    private fun callCallable(): T {
        latch.countDown()
        try {
            return callable.call()
        } catch (e: Exception) {
            throw ExecutionException(e)
        }

    }
}
