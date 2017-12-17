package io.fotoapparat.result.adapter.rxjava

import io.fotoapparat.result.PendingResult
import rx.Single
import java.util.concurrent.Future

/**
 * Adapter for [Single].
 */
object SingleAdapter {

    /**
     * @return Adapter which adapts result to [Single].
     */
    @JvmStatic
    fun <T> toSingle(): (Future<T>) -> Single<T> {
        return { future -> Single.from(future) }
    }

}

/**
 * @return A [Single] from the given [PendingResult].
 */
fun <T> PendingResult<T>.toSingle(): Single<T> {
    return adapt { future -> Single.from(future) }
}
