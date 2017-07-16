package io.fotoapparat.hardware;

/**
 * A generic camera exception.
 */
public class CameraException extends RuntimeException {

    public CameraException(Exception e) {
        super(e);
    }

    public CameraException(String message) {
        super(message);
    }

    public CameraException(String message, Throwable cause) {
        super(message, cause);
    }

}