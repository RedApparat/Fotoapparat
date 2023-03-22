package io.fotoapparat.result.adapter.rxjava3

import android.annotation.SuppressLint
import io.fotoapparat.result.PendingResult
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Future

/**
 * Adapter for [Observable].
 */
object ObservableAdapter {

    /**
     * @return Adapter which adapts result to [Observable].
     */
    @JvmStatic
    @SuppressLint("CheckResult")
    fun <T : Any> toObservable(): Function1<Future<T>, Observable<T>> {
        return { future -> Observable.fromFuture(future) }
    }

}

/**
 * @return A [Observable] from the given [PendingResult].
 */
@SuppressLint("CheckResult")
fun <T : Any> PendingResult<T>.toObservable(): Observable<T> {
    return adapt { future -> Observable.fromFuture(future) }
}
