package io.fotoapparat.hardware.operators;

import io.fotoapparat.photo.Photo;

/**
 * An interface which indicates that the class can
 * capture still pictures.
 */
public interface CaptureOperator {

    /**
     * Invokes a still picture capture action.
     *
     * @return The captured photo.
     */
    Photo takePicture();
}
