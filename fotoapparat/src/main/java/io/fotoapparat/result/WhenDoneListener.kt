package io.fotoapparat.result

/**
 * Listener called when an action is done.
 */
interface WhenDoneListener<in T> {

    /**
     * Called on when an action is done.
     */
    fun whenDone(it: T?)
}