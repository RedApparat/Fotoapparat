package io.fotoapparat.routine;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.photo.PhotoResult;
import io.fotoapparat.request.PhotoRequest;

/**
 * Applies {@link PhotoRequest} to camera and takes picture.
 */
public class TakePictureRoutine {

	private final CameraDevice cameraDevice;
	private final Executor cameraExecutor;

	public TakePictureRoutine(CameraDevice cameraDevice,
							  Executor cameraExecutor) {
		this.cameraDevice = cameraDevice;
		this.cameraExecutor = cameraExecutor;
	}

	/**
	 * Takes picture, returns immediately.
	 *
	 * @param photoRequest desired parameters of the photo.
	 * @return {@link PhotoResult} which will deliver result asynchronously.
	 */
	public PhotoResult takePicture(PhotoRequest photoRequest) {
		TakePictureTask takePictureTask = new TakePictureTask(cameraDevice);
		cameraExecutor.execute(takePictureTask);

		return PhotoResult.fromFuture(takePictureTask);
	}

}
