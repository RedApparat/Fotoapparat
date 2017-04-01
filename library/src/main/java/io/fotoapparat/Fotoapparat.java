package io.fotoapparat;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.orientation.ScreenOrientationProvider;
import io.fotoapparat.photo.PhotoResult;
import io.fotoapparat.request.PhotoRequest;
import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.routine.StopCameraRoutine;

/**
 * Camera. Takes pictures.
 */
public class Fotoapparat {

	private static final Executor SERIAL_EXECUTOR = Executors.newSingleThreadExecutor();

	private final StartCameraRoutine startCameraRoutine;
	private final StopCameraRoutine stopCameraRoutine;
	private final Executor executor;

	public Fotoapparat(StartCameraRoutine startCameraRoutine,
					   StopCameraRoutine stopCameraRoutine,
					   Executor executor) {
		this.startCameraRoutine = startCameraRoutine;
		this.stopCameraRoutine = stopCameraRoutine;
		this.executor = executor;
	}

	public static FotoapparatBuilder with(Context context) {
		return new FotoapparatBuilder(context);
	}

	static Fotoapparat create(FotoapparatBuilder builder) {
		CameraDevice cameraDevice = builder.cameraProvider.get(builder.logger);

		ScreenOrientationProvider screenOrientationProvider = new ScreenOrientationProvider(builder.context);

		StartCameraRoutine startCameraRoutine = new StartCameraRoutine(
				builder.availableLensPositionsProvider,
				cameraDevice,
				builder.renderer,
				builder.lensPositionSelector,
				screenOrientationProvider
		);

		StopCameraRoutine stopCameraRoutine = new StopCameraRoutine(cameraDevice);

		return new Fotoapparat(
				startCameraRoutine,
				stopCameraRoutine,
				SERIAL_EXECUTOR
		);
	}

	public Capabilities getCapabilities() {
		return null;
	}

	public PhotoResult takePhoto() {
		return null;
	}

	public PhotoResult takePhoto(PhotoRequest photoRequest) {
		return null;
	}

	public void start() {
		executor.execute(
				startCameraRoutine
		);
	}

	public void stop() {
		executor.execute(
				stopCameraRoutine
		);
	}

}
