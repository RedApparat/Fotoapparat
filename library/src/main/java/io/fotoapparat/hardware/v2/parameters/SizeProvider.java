package io.fotoapparat.hardware.v2.parameters;

import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;

/**
 * Provides information related to the {@link Size}s of the {@link io.fotoapparat.Fotoapparat}
 * session.
 */
public class SizeProvider {
	/**
	 * Max preview width that is guaranteed by Camera2 API
	 */
	private static final int MAX_PREVIEW_WIDTH = 1920;

	/**
	 * Max preview height that is guaranteed by Camera2 API
	 */
	private static final int MAX_PREVIEW_HEIGHT = 1080;

	private final ParametersManager parametersManager;

	public SizeProvider(ParametersManager parametersManager) {
		this.parametersManager = parametersManager;
	}

	/**
	 * Returns the still picture capture size.
	 *
	 * @return The size.
	 */
	public Size getStillCaptureSize() {
		return parametersManager.getParameters().getValue(Parameters.Type.PICTURE_SIZE);
	}

	/**
	 * The aspect ratio (width/height) based on the still picture capture size.
	 *
	 * @return The aspect ratio (width/height).
	 */
	public float getStillCaptureAspectRatio() {
		Size captureSize = getStillCaptureSize();
		return (float) captureSize.width / captureSize.height;
	}

}
