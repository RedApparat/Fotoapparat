package io.fotoapparat.hardware;

import io.fotoapparat.photo.Photo;

/**
 * Abstraction for camera hardware.
 */
public interface CameraDevice {

    void open(Parameters parameters);

    void close();

    void startPreview();

    void stopPreview();

    void setDisplaySurface(Object displaySurface);

    Photo takePicture();

}
