package io.fotoapparat.hardware.operators;

/**
 * An interface which indicates that the class can
 * start and stop the capture preview.
 */
public interface PreviewOperator {

    /**
     * Starts the preview to the surface.
     * <p>
     * {@link #stopPreview()} should be called
     * to stop the operation.
     */
    void startPreview();

    /**
     * Stops the preview from the surface.
     */
    void stopPreview();
}
