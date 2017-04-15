package io.fotoapparat.hardware.v2.parameters;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;

import static io.fotoapparat.parameter.Parameters.Type.FLASH;
import static io.fotoapparat.parameter.Parameters.Type.FOCUS_MODE;

/**
 * Manages the parameters of a {@link io.fotoapparat.hardware.CameraDevice}.
 */
public class ParametersProvider implements ParametersOperator {

	/**
	 * Max preview width that is guaranteed by Camera2 API
	 */
	private static final int MAX_PREVIEW_WIDTH = 1920;
	/**
	 * Max preview height that is guaranteed by Camera2 API
	 */
	private static final int MAX_PREVIEW_HEIGHT = 1080;
	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private Parameters parameters;

	@Override
	public void updateParameters(Parameters parameters) {
		this.parameters = parameters;
		countDownLatch.countDown();
	}

	/**
	 * Returns the last updated parameters. This will block the calling thread until the parameters
	 * have been obtained.
	 *
	 * @return the last updated parameters.
	 */
	private Parameters getParameters() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}
		return parameters;
	}

	/**
	 * Returns the flash firing mode of the camera.
	 *
	 * @return The flash firing mode.
	 */
	public Flash getFlash() {
		return getParameters().getValue(FLASH);
	}

	/**
	 * Returns the focus mode of the camera.
	 *
	 * @return The focus mode.
	 */
	public FocusMode getFocus() {
		return getParameters().getValue(FOCUS_MODE);
	}

	/**
	 * Returns the still picture capture size.
	 *
	 * @return The size.
	 */
	public Size getStillCaptureSize() {
		return getParameters().getValue(Parameters.Type.PICTURE_SIZE);
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
