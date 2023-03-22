package io.fotoapparat.result.adapter.rxjava3

import android.annotation.SuppressLint
import io.fotoapparat.result.PendingResult
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.Future

/**
 * Adapter for [Flowable].
 */
object FlowableAdapter {

    /**
     * @return Adapter which adapts result to [Flowable].
     */
    @JvmStatic
    @SuppressLint("CheckResult")
    fun <T : Any> toFlowable(): Function1<Future<T>, Flowable<T>> {
        return { future -> Flowable.fromFuture(future) }
    }

}

/**
 * @return A [Flowable] from the given [PendingResult].
 */
@SuppressLint("CheckResult")
fun <T : Any> PendingResult<T>.toFlowable(): Flowable<T> {
    return adapt { future -> Flowable.fromFuture(future) }
}
