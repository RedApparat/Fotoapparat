package io.fotoapparat.parameter;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.view.CameraRenderer;

/**
 * Parameters for {@link CameraRenderer} which are supplied by {@link CameraDevice}.
 */
public class RendererParameters {

    /**
     * Size of preview frame in pixels.
     */
    public final Size previewSize;

    /**
     * The clockwise rotation angle in degrees, relative to the orientation to the camera,
     * that the preview needs to be rotated by, to be viewed right.
     */
    public final int frameRotation;

    public RendererParameters(Size previewSize,
                              int frameRotation) {
        this.previewSize = previewSize;
        this.frameRotation = frameRotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RendererParameters that = (RendererParameters) o;

        return frameRotation == that.frameRotation && (previewSize != null ? previewSize.equals(that.previewSize) : that.previewSize == null);
    }

    @Override
    public int hashCode() {
        int result = previewSize != null ? previewSize.hashCode() : 0;
        result = 31 * result + frameRotation;
        return result;
    }

    @Override
    public String toString() {
        return "RendererParameters{" +
                "previewSize=" + previewSize +
                ", frameRotation=" + frameRotation +
                '}';
    }
}
