package io.fotoapparat.hardware;

import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.photo.Photo;

/**
 * Abstraction for camera hardware.
 */
public interface CameraDevice {

	void open(LensPosition lensPosition);

	void close();

	void startPreview();

	void stopPreview();

	void setDisplaySurface(Object displaySurface);

	void updateParameters(Parameters parameters);

	Photo takePicture();

}
