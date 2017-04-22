package io.fotoapparat.hardware.operators;

/**
 * An interface which indicates that the class can
 * handle the orientation updates.
 */
public interface OrientationOperator {

    /**
     * Sets the current orientation of the display.
     */
    void setDisplayOrientation(int degrees);
}
