package io.fotoapparat.result;

/**
 * Exception which is not caused by developer and can be either ignored or recovered from.
 */
public class RecoverableRuntimeException extends RuntimeException {

    public RecoverableRuntimeException() {
    }

    public RecoverableRuntimeException(String message) {
        super(message);
    }

    public RecoverableRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecoverableRuntimeException(Throwable cause) {
        super(cause);
    }

}
