package io.fotoapparat.test

import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * @return [Future] which is immediately ready to provide given value.
 */
internal fun <T> immediateFuture(value: T): Future<T> = object : Future<T> {
    override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        return false
    }

    override fun isCancelled(): Boolean {
        return false
    }

    override fun isDone(): Boolean {
        return true
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    override fun get(): T {
        return value
    }

    @Throws(InterruptedException::class, ExecutionException::class, TimeoutException::class)
    override fun get(timeout: Long, unit: TimeUnit): T {
        return value
    }
}
