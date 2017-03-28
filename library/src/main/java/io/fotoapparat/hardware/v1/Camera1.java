package io.fotoapparat.hardware.v1;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.Parameters;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.photo.Photo;

/**
 * Camera hardware driver for v1 {@link Camera} API.
 */
@SuppressWarnings("deprecation")
public class Camera1 implements CameraDevice {

	private Camera camera;

	@Override
	public void open(LensPosition lensPosition) {
		try {
			camera = Camera.open(
					cameraIdForLensPosition(lensPosition)
			);
		} catch (RuntimeException e) {
			throw new CameraException(e);
		}

		// TODO apply parameters

		camera.setErrorCallback(new Camera.ErrorCallback() {
			@Override
			public void onError(int error, Camera camera) {
				throw new IllegalStateException("Camera error code: " + error);
			}
		});
	}

	private int cameraIdForLensPosition(LensPosition lensPosition) {
		int numberOfCameras = Camera.getNumberOfCameras();

		for (int i = 0; i < numberOfCameras; i++) {
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);

			if (info.facing == facingForLensPosition(lensPosition)) {
				return i;
			}
		}

		return 0;
	}

	private int facingForLensPosition(LensPosition lensPosition) {
		switch (lensPosition) {
			case FRONT:
				return Camera.CameraInfo.CAMERA_FACING_FRONT;
			case BACK:
				return Camera.CameraInfo.CAMERA_FACING_BACK;
			default:
				throw new IllegalArgumentException("Camera is not supported: " + lensPosition);
		}
	}

	@Override
	public void close() {
		if (camera != null) {
			camera.release();
		}
	}

	@Override
	public void startPreview() {
		camera.startPreview();
	}

	@Override
	public void stopPreview() {
		camera.stopPreview();
	}

	@Override
	public void setDisplaySurface(Object displaySurface) {
		try {
			trySetDisplaySurface(displaySurface);
		} catch (IOException e) {
			throw new CameraException(e);
		}
	}

	@Override
	public void updateParameters(Parameters parameters) {
		// TODO actually do something
	}

	@Override
	public Capabilities getCapabilities() {
		// TODO: return the capabilties of the camera device
		return null;
	}

	private void trySetDisplaySurface(Object displaySurface) throws IOException {
		if (displaySurface instanceof SurfaceTexture) {
			camera.setPreviewTexture(((SurfaceTexture) displaySurface));
		} else if (displaySurface instanceof SurfaceHolder) {
			camera.setPreviewDisplay(((SurfaceHolder) displaySurface));
		} else {
			throw new IllegalArgumentException("Unsupported display surface: " + displaySurface);
		}
	}

	@Override
	public Photo takePicture() {
		final CountDownLatch latch = new CountDownLatch(1);

		camera.takePicture(
				null,
				null,
				null,
				new Camera.PictureCallback() {
					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						// TODO take result
						// TODO check current screen orientation
						latch.countDown();
					}
				}
		);

		try {
			latch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}

		return null;
	}

}
