package io.fotoapparat.hardware.v2.session;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;

import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.v2.captor.SurfaceReader;
import io.fotoapparat.hardware.v2.connection.CameraConnection;

/**
 * Manages a {@link android.hardware.camera2.CameraCaptureSession} of a {@link
 * io.fotoapparat.hardware.v2.Camera2}.
 */
@SuppressWarnings("NewApi")
public class SessionManager implements PreviewOperator, CameraConnection.Listener {

	private final SurfaceReader surfaceReader;
	private final CameraConnection connection;
	private Session session;

	public SessionManager(SurfaceReader surfaceReader, CameraConnection connection) {
		this.surfaceReader = surfaceReader;
		this.connection = connection;
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


	public void setSurface(SurfaceTexture surface) {
		session = PreviewSession.create(
				connection.getCamera(),
				surfaceReader,
				surface
		);
	}

	/**
	 * @return the currently opened capture session of the camera
	 */
	public CameraCaptureSession getCaptureSession() {
		if (session == null) {
			session = new Session(connection.getCamera(), surfaceReader);
		}
		return session.getCaptureSession();
	}

}
