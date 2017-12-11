package io.fotoapparat.exception

/**
 * Exception which is not caused by developer and can be either ignored or recovered from.
 */
open class RecoverableRuntimeException(message: String) : RuntimeException(message)