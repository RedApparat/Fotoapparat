package io.fotoapparat.result.adapter.rxjava

import io.fotoapparat.result.PendingResult
import rx.Completable
import java.util.concurrent.Future

/**
 * Adapter for [Completable].
 */
object CompletableAdapter {

    /**
     * @return Adapter which adapts result to [Completable].
     */
    @JvmStatic
    fun <T> toCompletable(): Function1<Future<T>, Completable> {
        return { future -> Completable.fromFuture(future) }
    }

}

/**
 * @return A [Completable] from the given [PendingResult].
 */
fun <T> PendingResult<T>.toCompletable(): Completable {
    return adapt { future -> Completable.fromFuture(future) }
}
