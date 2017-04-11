package io.fotoapparat.hardware.v2;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v2.capabilities.CapabilitiesFactory;
import io.fotoapparat.hardware.v2.captor.PictureCaptor;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.parameters.ParametersManager;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.hardware.v2.stream.PreviewStream2;
import io.fotoapparat.hardware.v2.surface.TextureManager;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.photo.Photo;
import io.fotoapparat.preview.PreviewStream;

/**
 * Camera hardware driver for v2 {@link Camera2} API.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2 implements CameraDevice {

	private final OrientationManager orientationManager;
	private final TextureManager textureManager;
	private final PreviewStream2 previewStream;
	private final CapabilitiesFactory capabilitiesFactory;
	private final CameraConnection connection;
	private final ParametersManager parametersManager;
	private final SessionManager sessionManager;
	private final PictureCaptor pictureCaptor;

	public Camera2(CameraConnection connection,
				   SessionManager sessionManager,
				   TextureManager textureManager,
				   OrientationManager orientationManager,
				   ParametersManager parametersManager,
				   CapabilitiesFactory capabilitiesFactory,
				   PictureCaptor pictureCaptor,
				   PreviewStream2 previewStream) {
		this.capabilitiesFactory = capabilitiesFactory;
		this.connection = connection;
		this.parametersManager = parametersManager;
		this.sessionManager = sessionManager;
		this.pictureCaptor = pictureCaptor;
		this.orientationManager = orientationManager;
		this.textureManager = textureManager;
		this.previewStream = previewStream;
	}

	@Override
	public void open(LensPosition lensPosition) {
		connection.open(lensPosition);
	}

	@Override
	public void close() {
		connection.close();
	}

	@Override
	public void startPreview() {
		sessionManager.startPreview();
	}

	@Override
	public void stopPreview() {
		sessionManager.stopPreview();
	}

	@Override
	public void setDisplaySurface(Object displaySurface) {
		textureManager.setDisplaySurface(displaySurface);
	}

	@Override
	public void setDisplayOrientation(int degrees) {
		orientationManager.setDisplayOrientation(degrees);
	}

	@Override
	public void updateParameters(Parameters parameters) {
		parametersManager.updateParameters(parameters);
	}

	@Override
	public Capabilities getCapabilities() {
		return capabilitiesFactory.getCapabilities();
	}

	@Override
	public Photo takePicture() {
		return pictureCaptor.takePicture();
	}

	@Override
	public PreviewStream getPreviewStream() {
		return previewStream;
	}
}
