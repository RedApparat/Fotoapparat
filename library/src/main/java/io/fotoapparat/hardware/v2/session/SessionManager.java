package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;

import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.surface.SurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;

/**
 * Manages a {@link android.hardware.camera2.CameraCaptureSession} of a {@link
 * io.fotoapparat.hardware.v2.Camera2}.
 */
@SuppressWarnings("NewApi")
public class SessionManager implements PreviewOperator, CameraConnection.Listener {

	private final SurfaceReader surfaceReader;
	private final CameraConnection connection;
	private final CaptureRequestFactory captureRequestFactory;
	private final TextureManager textureManager;
	private Session session;

	public SessionManager(SurfaceReader surfaceReader,
						  CameraConnection connection,
						  CaptureRequestFactory captureRequestFactory,
						  TextureManager textureManager) {
		this.surfaceReader = surfaceReader;
		this.connection = connection;
		this.captureRequestFactory = captureRequestFactory;
		this.textureManager = textureManager;
		connection.setListener(this);
	}

	@Override
	public void onConnectionClosed() {
		if (session != null) {
			session.close();
		}
	}

	@Override
	public void startPreview() {

		try {
			CameraDevice camera = connection.getCamera();
			Surface viewSurface = textureManager.getSurface();
			Surface captureSurface = surfaceReader.getSurface();
			CaptureRequest previewRequest = captureRequestFactory.createPreviewRequest();

			PreviewSession previewSession = new PreviewSession(
					camera,
					viewSurface,
					captureSurface,
					previewRequest
			);

			previewSession.startPreview();
			session = previewSession;
		} catch (CameraAccessException e) {
			// TODO: 09.04.17
		}
	}

	@Override
	public void stopPreview() {
		if (session instanceof PreviewSession) {
			((PreviewSession) session).stopPreview();
		}
	}

	/**
	 * @return the currently opened capture session of the camera
	 */
	public Session getCaptureSession() {
		if (session == null) {

			CameraDevice camera = connection.getCamera();
			Surface captureSurface = surfaceReader.getSurface();
			session = new Session(camera, captureSurface);
		}
		return session;
	}

}
