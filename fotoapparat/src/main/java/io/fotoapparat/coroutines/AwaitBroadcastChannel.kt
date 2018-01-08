package io.fotoapparat.coroutines

import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel

/**
 * A [ConflatedBroadcastChannel] which exposes a [getValue] which will [await] for at least one value.
 */
internal class AwaitBroadcastChannel<T>(
        private val channel: ConflatedBroadcastChannel<T> = ConflatedBroadcastChannel(),
        private val defered: CompletableDeferred<Boolean> = CompletableDeferred()
) : BroadcastChannel<T> by channel, Deferred<Boolean> by defered {

    /**
     * The most recently sent element to this channel.
     */
    suspend fun getValue(): T {
        defered.await()
        return channel.value
    }

    override fun offer(element: T): Boolean {
        defered.complete(true)
        return channel.offer(element)
    }

    suspend override fun send(element: T) {
        defered.complete(true)
        channel.send(element)
    }
}