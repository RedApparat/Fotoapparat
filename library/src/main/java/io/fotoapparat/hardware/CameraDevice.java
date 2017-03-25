package io.fotoapparat.hardware;

import android.graphics.SurfaceTexture;

import io.fotoapparat.photo.Photo;

/**
 * Abstraction for camera hardware.
 */
public interface CameraDevice {

    void open(Parameters parameters);

    void close();

    void startPreview();

    void stopPreview();

    void setDisplaySurface(SurfaceTexture displaySurface);

    Photo takePicture();

}
