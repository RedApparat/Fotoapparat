package io.fotoapparat.result.adapter.rxjava

import io.fotoapparat.result.PendingResult
import rx.Observable
import java.util.concurrent.Future

/**
 * Adapter for [Observable].
 */
object ObservableAdapter {

    /**
     * @return Adapter which adapts result to [Observable].
     */
    @JvmStatic
    fun <T> toObservable(): Function1<Future<T>, Observable<T>> {
        return { future -> Observable.from(future) }
    }

}

/**
 * @return A [Observable] from the given [PendingResult].
 */
fun <T> PendingResult<T>.toObservable(): Observable<T> {
    return adapt { future -> Observable.from(future) }
}
