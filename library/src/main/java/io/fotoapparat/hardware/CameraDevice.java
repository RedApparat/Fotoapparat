package io.fotoapparat.hardware;

import android.view.View;

/**
 * Abstraction for camera hardware.
 */
public interface CameraDevice {

	void open();

	void close();

	void startPreview();

	void stopPreview();

	void setDisplaySurface(View displaySurface);

	void updateParameters(Parameters parameters);

	void takePicture(PhotoCallback callback);

}
