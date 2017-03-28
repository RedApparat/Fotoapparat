package io.fotoapparat.hardware.v2;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.v2.capabilities.CameraCapabilities;
import io.fotoapparat.hardware.v2.captor.PhotoCaptor;
import io.fotoapparat.hardware.v2.session.PreviewOperator;
import io.fotoapparat.hardware.v2.session.PreviewSession;
import io.fotoapparat.hardware.v2.session.Session;
import io.fotoapparat.photo.Photo;

/**
 * Wrapper around the internal {@link android.hardware.camera2.CameraManager}.
 * <p>
 * Facilitates operations such as preview, taking pictures and managing the camera.
 */
@SuppressWarnings("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class CameraManager implements PreviewOperator, PhotoCaptor {

	private final OpenCameraAction openCameraAction = new OpenCameraAction();
	private final android.hardware.camera2.CameraManager manager;
	private Session session;
	private CameraCapabilities capabilities;

	CameraManager(android.hardware.camera2.CameraManager manager) {
		this.manager = manager;
		capabilities = new CameraCapabilities(manager);
	}

	CameraCharacteristics getCapabilities() {
		return capabilities.getCameraCharacteristics();
	}

	void open(final String cameraId) {
		CameraThread
				.getInstance()
				.getHandler()
				.post(new Runnable() {

					@Override
					public void run() {
						try {
							manager.openCamera(cameraId, openCameraAction, null);
							capabilities.setCamera(cameraId);
						} catch (CameraAccessException e) {
							// do nothing
						}
					}
				});
	}

	void close() {
		getCamera().close();
	}

	android.hardware.camera2.CameraDevice getCamera() {
		return openCameraAction.getCamera();
	}

	public void setSurface(SurfaceTexture displaySurface) {
		session = new PreviewSession(getCamera(), getCapabilities(), displaySurface);
	}

	@Override
	public void startPreview() {
		if (session instanceof PreviewSession) {
			((PreviewSession) session).startPreview();
		}
	}

	@Override
	public void stopPreview() {
		if (session instanceof PreviewSession) {
			((PreviewSession) session).stopPreview();
		}
	}

	@Override
	public Photo takePicture() {
		if (session == null) {
			session = new Session(getCamera(), getCapabilities());
		}
		return session.takePicture();
	}

}
