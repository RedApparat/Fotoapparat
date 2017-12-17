package io.fotoapparat.result.adapter.rxjava2

import android.annotation.SuppressLint
import io.fotoapparat.result.PendingResult
import io.reactivex.Completable
import java.util.concurrent.Future

/**
 * Adapter for [Completable].
 */
object CompletableAdapter {

    /**
     * @return Adapter which adapts result to [Completable].
     */
    @JvmStatic
    @SuppressLint("CheckResult")
    fun <R> toCompletable(): (Future<R>) -> Completable {
        return { future -> Completable.fromFuture(future) }
    }

}

/**
 * @return A [Completable] from the given [PendingResult].
 */
@SuppressLint("CheckResult")
fun <T> PendingResult<T>.toCompletable(): Completable {
    return adapt { future -> Completable.fromFuture(future) }
}

