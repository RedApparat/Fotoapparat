package io.fotoapparat.routine;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.photo.Photo;

/**
 * Takes photo and returns result as {@link Photo}.
 */
class TakePictureTask extends FutureTask<Photo> {

	public TakePictureTask(final CameraDevice cameraDevice) {
		super(new Callable<Photo>() {
			@Override
			public Photo call() throws Exception {
				cameraDevice.autoFocus();

				Photo photo = cameraDevice.takePicture();

				cameraDevice.startPreview();

				return photo;
			}
		});
	}

}
