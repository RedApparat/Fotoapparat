package io.fotoapparat;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.photo.PhotoResult;
import io.fotoapparat.request.PhotoRequest;
import io.fotoapparat.routine.StartCameraRoutine;

/**
 * Camera. Takes pictures.
 */
public class Fotoapparat {

	private final StartCameraRoutine startCameraRoutine;
	private final Executor executor;

	public Fotoapparat(StartCameraRoutine startCameraRoutine,
					   Executor executor) {
		this.startCameraRoutine = startCameraRoutine;
		this.executor = executor;
	}

	public static FotoapparatBuilder with(Context context) {
		return new FotoapparatBuilder();
	}

	static Fotoapparat create(FotoapparatBuilder builder) {
		return new Fotoapparat(
				new StartCameraRoutine(
						builder.availableLensPositionsProvider,
						builder.cameraProvider.get(),
						builder.renderer,
						builder.lensPositionSelector
				),
				Executors.newSingleThreadExecutor()
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

	}
}
