package io.fotoapparat.hardware.v2;

import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.TextureView;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.Parameters;
import io.fotoapparat.hardware.v2.session.PreviewOperator;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.photo.Photo;

/**
 * Camera hardware driver for v2 {@link Camera2} API.
 */
@SuppressWarnings("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2 implements CameraDevice, PreviewOperator, OrientationObserver, OrientationObserver.OrientationListener {

	private final TextureManager textureManager = new TextureManager(this);
	private final io.fotoapparat.hardware.v2.CameraManager cameraManager;
	private OrientationListener orientationListener;

	public Camera2(io.fotoapparat.hardware.v2.CameraManager cameraManager) {
		this.cameraManager = cameraManager;
	}

	@Override
	public void open(LensPosition lensPosition) {
		try {
			cameraManager.open(lensPosition);
		} catch (CameraAccessException e) {
			// // TODO: 02/04/17  fail gracefully
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
		if (displaySurface instanceof TextureView) {
			textureManager.setTextureView((TextureView) displaySurface);

			cameraManager.setSurface(textureManager.getSurface());
		} else {
			throw new IllegalArgumentException("Unsupported display surface: " + displaySurface);
		}
	}

	@Override
	public void setDisplayOrientation(int degrees) {
		if (orientationListener != null) {
			orientationListener.setDisplayOrientation(degrees);
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

	@Override
	public Photo takePicture() {
		try {
			return cameraManager.takePicture();
		} catch (CameraAccessException e) {
			// // TODO: 02/04/17  fail gracefully
			throw new CameraException(e);
		}
	}

	@Override
	public void setOrientationListener(@NonNull OrientationListener orientationListener) {
		this.orientationListener = orientationListener;
	}
}
