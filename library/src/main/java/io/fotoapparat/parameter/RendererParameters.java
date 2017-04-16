package io.fotoapparat.parameter;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.view.CameraRenderer;

/**
 * Parameters for {@link CameraRenderer} which are supplied by {@link CameraDevice}.
 */
public class RendererParameters {

	/**
	 * Size of preview frame in pixels.
	 */
	public final Size previewSize;

	/**
	 * Clockwise rotation of preview frames in degrees.
	 */
	public final int frameRotation;

	public RendererParameters(Size previewSize,
							  int frameRotation) {
		this.previewSize = previewSize;
		this.frameRotation = frameRotation;
	}

}
