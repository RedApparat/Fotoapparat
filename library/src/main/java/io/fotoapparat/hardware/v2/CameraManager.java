package io.fotoapparat.hardware.v2;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.v2.captor.PictureCaptor;
import io.fotoapparat.hardware.v2.captor.SurfaceReader;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.session.PreviewOperator;
import io.fotoapparat.hardware.v2.session.PreviewSession;
import io.fotoapparat.hardware.v2.session.Session;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.photo.Photo;

/**
 * Wrapper around the internal {@link android.hardware.camera2.CameraManager}.
 * <p>
 * Facilitates operations such as preview, taking pictures and managing the camera.
 */
@SuppressWarnings("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraManager implements PreviewOperator {

	private final CameraSelector cameraSelector;
	private final CameraConnection connection;
	private final SurfaceReader surfaceReader;
	private final PictureCaptor pictureCaptor;
	private Session session;

	public CameraManager(CameraSelector cameraSelector,
						 CameraConnection connection,
						 SurfaceReader surfaceReader,
						 PictureCaptor pictureCaptor) {
		this.cameraSelector = cameraSelector;
		this.connection = connection;
		this.surfaceReader = surfaceReader;
		this.pictureCaptor = pictureCaptor;
	}

	void open(LensPosition lensPosition) throws CameraAccessException {
		String cameraId = cameraSelector.findCameraId(lensPosition);
		connection.openById(cameraId);
	}

	void close() {
		connection.close();
		if (session != null) {
			session.close();
		}
	}

	public void setSurface(SurfaceTexture surfaceTexture) {
		CameraDevice camera = getCamera();
		session = PreviewSession.create(camera, surfaceReader, surfaceTexture);
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

	public Photo takePicture() throws CameraAccessException {
		if (session == null) {
			session = new Session(getCamera(), surfaceReader);
		}
		return pictureCaptor.takePhoto(session.getCaptureSession());
	}

	private android.hardware.camera2.CameraDevice getCamera() {
		return connection.getCamera();
	}
}
