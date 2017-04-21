package io.fotoapparat.hardware.operators;

/**
 * An interface which indicates that the class can
 * set a preview surface.
 */
public interface SurfaceOperator {

    /**
     * Sets the desired surface on which the camera preview will be displayed.
     */
    void setDisplaySurface(Object displaySurface);
}
