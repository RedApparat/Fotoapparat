package io.fotoapparat.hardware.v2;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.TextureView;

import io.fotoapparat.hardware.v2.capabilities.CameraCapabilities;
import io.fotoapparat.hardware.v2.captor.PhotoCaptor;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
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
class CameraManager implements PreviewOperator, PhotoCaptor, OrientationObserver, OrientationObserver.OrientationListener {

	private final TextureManager textureManager = new TextureManager(this);
	private final CameraConnection cameraConnection;
	private Session session;
	private CameraCapabilities capabilities;
	private OrientationListener orientationListener;

	CameraManager(CameraConnection cameraConnection, CameraCapabilities capabilities) {
		this.cameraConnection = cameraConnection;
		this.capabilities = capabilities;
	}

	CameraCharacteristics getCapabilities() {
		return capabilities.getCameraCharacteristics();
	}

	void open(final String cameraId) throws CameraAccessException {
		cameraConnection.open(cameraId);
		capabilities.setCameraId(cameraId);
	}

	void close() {
		getCamera().close();
	}

	android.hardware.camera2.CameraDevice getCamera() {
		return cameraConnection.getCamera();
	}

	public void setSurface(TextureView textureView) {
		textureManager.setTextureView(textureView);

		CameraDevice camera = getCamera();
		CameraCharacteristics capabilities = getCapabilities();
		SurfaceTexture surface = textureManager.getSurface();
		session = new PreviewSession(camera, capabilities, surface);
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

	@Override
	public void setOrientationListener(@NonNull OrientationListener orientationListener) {
		this.orientationListener = orientationListener;
	}

	@Override
	public void onOrientationChanged(int orientation) {
		if (orientationListener != null) {
			orientationListener.onOrientationChanged(orientation);
		}
	}
}
