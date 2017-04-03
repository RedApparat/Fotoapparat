package io.fotoapparat.hardware;

import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
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

	void setDisplayOrientation(int degrees);

	void updateParameters(Parameters parameters);

	Capabilities getCapabilities();

	Photo takePicture();

}
