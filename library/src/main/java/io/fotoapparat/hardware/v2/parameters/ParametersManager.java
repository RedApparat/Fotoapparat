package io.fotoapparat.hardware.v2.parameters;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.parameter.Parameters;

/**
 * Manages the parameters of a {@link io.fotoapparat.hardware.CameraDevice}.
 */
public class ParametersManager implements ParametersOperator {

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
	public Parameters getParameters() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}
		return parameters;
	}
}
