package io.fotoapparat.view;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.parameter.ScaleType;

/**
 * Renders the stream from {@link io.fotoapparat.Fotoapparat}.
 */
public interface CameraRenderer {

    /**
     * Sets the scale type of the preview to the renderer. This method will be called from camera
     * thread, so it is safe to perform blocking operations here.
     */
    void setScaleType(ScaleType scaleType);

    /**
     * Attaches renderer to camera, so that it will display the preview when camera is started. This
     * method will be called from camera thread, so it is safe to perform blocking operations here.
     */
    void attachCamera(CameraDevice camera);

}
