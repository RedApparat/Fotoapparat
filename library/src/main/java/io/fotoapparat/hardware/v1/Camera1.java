package io.fotoapparat.hardware.v1;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.Parameters;
import io.fotoapparat.hardware.orientation.OrientationUtils;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.photo.Photo;

/**
 * Camera hardware driver for v1 {@link Camera} API.
 */
@SuppressWarnings("deprecation")
public class Camera1 implements CameraDevice {

	private final Logger logger;

	private Camera camera;
	private int cameraId;

	private Throwable lastStacktrace;

	public Camera1(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void open(LensPosition lensPosition) {
		recordMethod();

		try {
			cameraId = cameraIdForLensPosition(lensPosition);
			camera = Camera.open(cameraId);
		} catch (RuntimeException e) {
			throw new CameraException(e);
		}

		// TODO apply parameters

		camera.setErrorCallback(new Camera.ErrorCallback() {
			@Override
			public void onError(int error, Camera camera) {
				if (lastStacktrace != null) {
					lastStacktrace.printStackTrace();
				}

				throw new IllegalStateException("Camera error code: " + error);
			}
		});
	}

	private int cameraIdForLensPosition(LensPosition lensPosition) {
		int numberOfCameras = Camera.getNumberOfCameras();

		for (int i = 0; i < numberOfCameras; i++) {
			Camera.CameraInfo info = getCameraInfo(i);

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
		recordMethod();

		if (camera != null) {
			camera.release();
		}
	}

	@Override
	public void startPreview() {
		recordMethod();

		camera.startPreview();
	}

	@Override
	public void stopPreview() {
		recordMethod();

		camera.stopPreview();
	}

	@Override
	public void setDisplaySurface(Object displaySurface) {
		recordMethod();

		try {
			trySetDisplaySurface(displaySurface);
		} catch (IOException e) {
			throw new CameraException(e);
		}
	}

	@Override
	public void setDisplayOrientation(int degrees) {
		recordMethod();

		degrees = OrientationUtils.toClosestRightAngle(degrees);

		Camera.CameraInfo info = getCameraInfo(cameraId);
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			degrees = (info.orientation + degrees) % 360;
			degrees = (360 - degrees) % 360;
		} else {
			degrees = (info.orientation - degrees + 360) % 360;
		}

		camera.setDisplayOrientation(degrees);
	}

	@Override
	public void updateParameters(Parameters parameters) {
		recordMethod();
		// TODO actually do something
	}

	@Override
	public Capabilities getCapabilities() {
		recordMethod();
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
		recordMethod();

		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Photo> photoReference = new AtomicReference<>();

		camera.takePicture(
				null,
				null,
				null,
				new Camera.PictureCallback() {
					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						photoReference.set(
								new Photo(
										data,
										0 // TODO check current screen orientation
								)
						);

						latch.countDown();
					}
				}
		);

		try {
			latch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}

		return photoReference.get();
	}

	@NonNull
	private Camera.CameraInfo getCameraInfo(int id) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(id, info);
		return info;
	}

	private void recordMethod() {
		lastStacktrace = new Exception();

		logger.log(
				lastStacktrace.getStackTrace()[1].getMethodName()
		);
	}

}
