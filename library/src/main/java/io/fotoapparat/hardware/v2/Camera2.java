package io.fotoapparat.hardware.v2;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.Parameters;
import io.fotoapparat.hardware.v2.captor.PhotoCaptor;
import io.fotoapparat.hardware.v2.session.PreviewOperator;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.photo.Photo;

/**
 * Camera hardware driver for v2 {@link Camera2} API.
 */
@SuppressWarnings("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2 implements CameraDevice, PreviewOperator, PhotoCaptor {

	private final CameraManager manager;
	private final io.fotoapparat.hardware.v2.CameraManager cameraManager;

	public Camera2(Context context) {
		manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
		cameraManager = new io.fotoapparat.hardware.v2.CameraManager(manager);
	}

	@Override
	public void open(LensPosition lensPosition) {
		try {
			final String[] cameraIdList = manager.getCameraIdList();

			// todo choose camera

			cameraManager.open(cameraIdList[0]);
		} catch (CameraAccessException e) {
			throw new CameraException(e);
		}
	}

	@Override
	public void close() {
		cameraManager.close();
	}

	@Override
	public void startPreview() {
		cameraManager.startPreview();
	}

	@Override
	public void stopPreview() {
		cameraManager.stopPreview();
	}

	@Override
	public void setDisplaySurface(Object displaySurface) {
		if (displaySurface instanceof SurfaceTexture) {
			cameraManager.setSurface(((SurfaceTexture) displaySurface));
		} else {
			throw new IllegalArgumentException("Unsupported display surface: " + displaySurface);
		}
	}

	@Override
	public void updateParameters(Parameters parameters) {
		// TODO actually do something
	}

	@Override
	public Photo takePicture() {
		return cameraManager.takePicture();
	}
}
