package io.fotoapparat.result.adapter.rxjava2

import android.annotation.SuppressLint
import io.fotoapparat.result.PendingResult
import io.reactivex.Single
import java.util.concurrent.Future

/**
 * Adapter for [Single].
 */
object SingleAdapter {

    /**
     * @return Adapter which adapts result to [Single].
     */
    @JvmStatic
    @SuppressLint("CheckResult")
    fun <T> toSingle(): (Future<T>) -> Single<T> {
        return { future -> Single.fromFuture(future) }
    }

}

/**
 * @return A [Single] from the given [PendingResult].
 */
@SuppressLint("CheckResult")
fun <T> PendingResult<T>.toSingle(): Single<T> {
    return adapt { future -> Single.fromFuture(future) }
}
