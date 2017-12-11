package io.fotoapparat.exception

/**
 * Thrown when there is a problem while saving the file.
 */
class FileSaveException(cause: Throwable) : RuntimeException(cause)