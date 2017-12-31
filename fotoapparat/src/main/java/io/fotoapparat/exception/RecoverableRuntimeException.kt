package io.fotoapparat.exception

/**
 * Exception which is not caused by developer and can be either ignored or recovered from.
 */
open class RecoverableRuntimeException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(throwable: Throwable) : super(throwable)
}