package io.fotoapparat.hardware.v2;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.TextureView;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.hardware.v2.captor.PictureCaptor;
import io.fotoapparat.hardware.v2.captor.SurfaceReader;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.orientation.TextureManager;
import io.fotoapparat.hardware.v2.selection.CameraSelector;
import io.fotoapparat.hardware.v2.session.PreviewOperator;
import io.fotoapparat.hardware.v2.session.PreviewSession;
import io.fotoapparat.hardware.v2.session.Session;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.photo.Photo;

/**
 * Camera hardware driver for v2 {@link Camera2} API.
 */
@SuppressWarnings("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2 implements CameraDevice, PreviewOperator {

	private final OrientationManager orientationManager;
	private final TextureManager textureManager;
	private final CameraSelector cameraSelector;
	private final CameraConnection connection;
	private final SurfaceReader surfaceReader;
	private final PictureCaptor pictureCaptor;

	private Session session;

	public Camera2(CameraSelector cameraSelector,
				   CameraConnection connection,
				   SurfaceReader surfaceReader,
				   PictureCaptor pictureCaptor,
				   OrientationManager orientationManager,
				   TextureManager textureManager) {
		this.cameraSelector = cameraSelector;
		this.connection = connection;
		this.surfaceReader = surfaceReader;
		this.pictureCaptor = pictureCaptor;
		this.orientationManager = orientationManager;
		this.textureManager = textureManager;
	}

	@Override
	public void open(LensPosition lensPosition) {
		String cameraId = cameraSelector.findCameraId(lensPosition);
		connection.openById(cameraId);
	}

	@Override
	public void close() {
		connection.close();
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

	@Override
	public void setDisplaySurface(Object displaySurface) {
		if (displaySurface instanceof TextureView) {
			textureManager.setTextureView((TextureView) displaySurface);

			session = PreviewSession.create(
					connection.getCamera(),
					surfaceReader,
					textureManager.getSurface()
			);
		} else {
			throw new IllegalArgumentException("Unsupported display surface: " + displaySurface);
		}
	}

	@Override
	public void setDisplayOrientation(int degrees) {
		orientationManager.onDisplayOrientationChanged(degrees);
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
		if (session == null) {
			session = new Session(connection.getCamera(), surfaceReader);
		}
		return pictureCaptor.takePhoto(session.getCaptureSession());
	}
}
