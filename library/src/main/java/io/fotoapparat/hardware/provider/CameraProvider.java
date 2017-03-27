package io.fotoapparat.hardware.provider;

import io.fotoapparat.hardware.CameraDevice;

/**
 * Abstraction for providing camera.
 */
public interface CameraProvider {

	/**
	 * @return a {@link CameraDevice}.
	 */
	CameraDevice get();

}
