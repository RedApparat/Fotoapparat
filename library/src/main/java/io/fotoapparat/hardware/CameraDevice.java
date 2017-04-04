package io.fotoapparat.hardware;

import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.photo.Photo;

/**
 * Abstraction for camera hardware.
 */
public interface CameraDevice {

	/**
	 * Opens a connection to a camera.
	 *
	 * @param lensPosition The camera position relatively to the screen of the device,
	 *                     which will determine which camera to open.
	 */
	void open(LensPosition lensPosition);

	/**
	 * Closes the connection to a camera.
	 */
	void close();

	/**
	 * Starts the preview of the camera.
	 */
	void startPreview();

	/**
	 * Stops the preview of the camera.
	 */
	void stopPreview();

	/**
	 * Sets the desired surface on which the camera preview will be displayed.
	 */
	void setDisplaySurface(Object displaySurface);

	/**
	 * Sets the current orientation of the display.
	 */
	void setDisplayOrientation(int degrees);

	/**
	 * Updates the desired parameters for the preview and the photo capture actions.
	 */
	void updateParameters(Parameters parameters);

	/**
	 * Returns the {@link Capabilities} of the opened camera.
	 *
	 * @return The {@link Capabilities} of the camera.
	 */
	Capabilities getCapabilities();

	/**
	 * Invokes a still picture capture action.
	 *
	 * @return The captured photo.
	 */
	Photo takePicture();

}
