package io.fotoapparat.routine;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.DisplayOrientationSensor;

/**
 * Observes current device orientation and updates {@link CameraDevice} accordingly.
 */
public class UpdateOrientationRoutine implements DisplayOrientationSensor.Listener {

	private final CameraDevice cameraDevice;
	private final DisplayOrientationSensor displayOrientationSensor;
	private final Executor cameraExecutor;

	public UpdateOrientationRoutine(CameraDevice cameraDevice,
									DisplayOrientationSensor displayOrientationSensor,
									Executor cameraExecutor) {
		this.cameraDevice = cameraDevice;
		this.displayOrientationSensor = displayOrientationSensor;
		this.cameraExecutor = cameraExecutor;
	}

	/**
	 * Starts routine.
	 */
	public void start() {
		displayOrientationSensor.start(this);
	}

	/**
	 * Stops routine.
	 */
	public void stop() {
		displayOrientationSensor.stop();
	}

	@Override
	public void onOrientationChanged(final int degrees) {
		cameraExecutor.execute(new Runnable() {
			@Override
			public void run() {
				cameraDevice.setDisplayOrientation(degrees);
			}
		});
	}

}
