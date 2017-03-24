package io.fotoapparat.hardware;

/**
 * Abstraction for providing camera.
 */
public interface CameraProvider {

	/**
	 * @return a {@link CameraDevice}.
	 */
	CameraDevice get();

}
