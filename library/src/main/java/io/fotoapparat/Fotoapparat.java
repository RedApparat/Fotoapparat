package io.fotoapparat;

import android.app.Activity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.orientation.ScreenOrientationProvider;
import io.fotoapparat.photo.PhotoResult;
import io.fotoapparat.request.PhotoRequest;
import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.routine.StopCameraRoutine;
import io.fotoapparat.routine.TakePictureRoutine;

/**
 * Camera. Takes pictures.
 */
public class Fotoapparat {

	private static final Executor SERIAL_EXECUTOR = Executors.newSingleThreadExecutor();

	private final StartCameraRoutine startCameraRoutine;
	private final StopCameraRoutine stopCameraRoutine;
	private final TakePictureRoutine takePictureRoutine;
	private final Executor executor;

	private boolean started = false;

	public Fotoapparat(StartCameraRoutine startCameraRoutine,
					   StopCameraRoutine stopCameraRoutine,
					   TakePictureRoutine takePictureRoutine,
					   Executor executor) {
		this.startCameraRoutine = startCameraRoutine;
		this.stopCameraRoutine = stopCameraRoutine;
		this.takePictureRoutine = takePictureRoutine;
		this.executor = executor;
	}

	public static FotoapparatBuilder with(Activity activity) {
		return new FotoapparatBuilder(activity);
	}

	static Fotoapparat create(FotoapparatBuilder builder) {
		CameraDevice cameraDevice = builder.cameraProvider.get(builder.logger);

		ScreenOrientationProvider screenOrientationProvider = new ScreenOrientationProvider(builder.activity);

		StartCameraRoutine startCameraRoutine = new StartCameraRoutine(
				builder.availableLensPositionsProvider,
				cameraDevice,
				builder.renderer,
				builder.lensPositionSelector,
				screenOrientationProvider
		);

		StopCameraRoutine stopCameraRoutine = new StopCameraRoutine(cameraDevice);

		TakePictureRoutine takePictureRoutine = new TakePictureRoutine(cameraDevice, SERIAL_EXECUTOR);

		return new Fotoapparat(
				startCameraRoutine,
				stopCameraRoutine,
				takePictureRoutine,
				SERIAL_EXECUTOR
		);
	}

	public Capabilities getCapabilities() {
		return null;
	}

	public final PhotoResult takePicture() {
		return takePicture(
				PhotoRequest.empty()
		);
	}

	public PhotoResult takePicture(PhotoRequest photoRequest) {
		ensureStarted();

		return takePictureRoutine.takePicture(
				photoRequest
		);
	}

	/**
	 * Starts camera.
	 *
	 * @throws IllegalStateException if camera was already started.
	 */
	public void start() {
		ensureNotStarted();
		started = true;

		executor.execute(
				startCameraRoutine
		);
	}

	/**
	 * Stops camera.
	 *
	 * @throws IllegalStateException if camera is not started.
	 */
	public void stop() {
		ensureStarted();
		started = false;

		executor.execute(
				stopCameraRoutine
		);
	}

	private void ensureStarted() {
		if (!started) {
			throw new IllegalStateException("Camera is not started!");
		}
	}

	private void ensureNotStarted() {
		if (started) {
			throw new IllegalStateException("Camera is already started!");
		}
	}

}
