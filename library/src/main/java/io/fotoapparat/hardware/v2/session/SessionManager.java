package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;

import java.util.Arrays;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.surface.ContinuousSurfaceReader;
import io.fotoapparat.hardware.v2.surface.StillSurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;

/**
 * Manages a {@link android.hardware.camera2.CameraCaptureSession} of a {@link
 * io.fotoapparat.hardware.v2.Camera2}.
 */
@SuppressWarnings("NewApi")
public class SessionManager implements PreviewOperator, CameraConnection.Listener {

	private final StillSurfaceReader surfaceReader;
	private final ContinuousSurfaceReader continuousSurfaceReader;
	private final CameraConnection connection;
	private final CaptureRequestFactory captureRequestFactory;
	private final TextureManager textureManager;
	private Session session;

	public SessionManager(StillSurfaceReader surfaceReader,
						  ContinuousSurfaceReader continuousSurfaceReader,
						  CameraConnection connection,
						  CaptureRequestFactory captureRequestFactory,
						  TextureManager textureManager) {
		this.surfaceReader = surfaceReader;
		this.continuousSurfaceReader = continuousSurfaceReader;
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
			Surface previewSurface = textureManager.getSurface();
			Surface captureSurface = surfaceReader.getSurface();
			Surface frameSurface = continuousSurfaceReader.getSurface();
			CaptureRequest previewRequest = captureRequestFactory.createPreviewRequest();

			PreviewSession previewSession = new PreviewSession(
					camera,
					previewRequest,
					Arrays.asList(previewSurface, captureSurface, frameSurface)
			);

			previewSession.startPreview();
			session = previewSession;
		} catch (CameraAccessException e) {
			throw new CameraException(e);
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
