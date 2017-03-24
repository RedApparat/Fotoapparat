package io.fotoapparat.hardware;

/**
 * Abstraction for camera hardware.
 */
public interface CameraDevice {

	void open();

	void close();

	void startPreview();

	void stopPreview();

	void setDisplaySurface(Object displaySurface);

	void updateParameters(Parameters parameters);

	void takePicture(PhotoCallback callback);

}
