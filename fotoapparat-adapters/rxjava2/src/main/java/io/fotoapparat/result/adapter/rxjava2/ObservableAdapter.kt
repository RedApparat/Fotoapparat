package io.fotoapparat.result.adapter.rxjava2

import android.annotation.SuppressLint
import io.fotoapparat.result.PendingResult
import io.reactivex.Observable
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
    fun <T> toObservable(): Function1<Future<T>, Observable<T>> {
        return { future -> Observable.fromFuture(future) }
    }

}

/**
 * @return A [Observable] from the given [PendingResult].
 */
@SuppressLint("CheckResult")
fun <T> PendingResult<T>.toObservable(): Observable<T> {
    return adapt { future -> Observable.fromFuture(future) }
}
