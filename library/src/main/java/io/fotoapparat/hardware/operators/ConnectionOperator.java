package io.fotoapparat.hardware.operators;

import io.fotoapparat.parameter.LensPosition;

/**
 * An interface which indicates that the class can
 * open and close connections to a camera.
 */
public interface ConnectionOperator {

    /**
     * Opens a connection to a camera.
     *
     * @param lensPosition The camera position relatively to the screen of the device,
     *                     which will determine which camera to open.
     */
    void open(LensPosition lensPosition);

    /**
     * Closes the connection to a camera.
     */
    void close();
}
