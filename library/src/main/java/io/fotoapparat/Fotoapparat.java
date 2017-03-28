package io.fotoapparat;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.orientation.OrientationSensor;
import io.fotoapparat.photo.PhotoResult;
import io.fotoapparat.request.PhotoRequest;
import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.routine.UpdateOrientationRoutine;

/**
 * Camera. Takes pictures.
 */
public class Fotoapparat {

	private final StartCameraRoutine startCameraRoutine;
	private final UpdateOrientationRoutine updateOrientationRoutine;
	private final Executor executor;

	public Fotoapparat(StartCameraRoutine startCameraRoutine,
					   UpdateOrientationRoutine updateOrientationRoutine,
					   Executor executor) {
		this.startCameraRoutine = startCameraRoutine;
		this.updateOrientationRoutine = updateOrientationRoutine;
		this.executor = executor;
	}

	public static FotoapparatBuilder with(Context context) {
		return new FotoapparatBuilder(context);
	}

	static Fotoapparat create(FotoapparatBuilder builder) {
		CameraDevice cameraDevice = builder.cameraProvider.get();
		ExecutorService cameraExecutor = Executors.newSingleThreadExecutor();

		return new Fotoapparat(
				new StartCameraRoutine(
						builder.availableLensPositionsProvider,
						cameraDevice,
						builder.renderer,
						builder.lensPositionSelector
				),
				new UpdateOrientationRoutine(
						cameraDevice,
						new OrientationSensor(builder.context),
						cameraExecutor
				),
				cameraExecutor
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
		updateOrientationRoutine.start();
	}

	public void stop() {
		updateOrientationRoutine.stop();
	}

}
