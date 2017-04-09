package io.fotoapparat.hardware.v2.parameters;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.surface.SurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;

import static io.fotoapparat.parameter.Parameters.Type.FLASH;
import static io.fotoapparat.parameter.Parameters.Type.FOCUS_MODE;

/**
 * Creates {@link CaptureRequest}s for a {@link android.hardware.camera2.CameraCaptureSession}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CaptureRequestFactory {

	private final SurfaceReader surfaceReader;
	private final TextureManager textureManager;
	private final ParametersManager parametersManager;
	private final Characteristics characteristics;
	private CameraConnection cameraConnection;

	public CaptureRequestFactory(CameraConnection cameraConnection,
								 SurfaceReader surfaceReader,
								 TextureManager textureManager,
								 ParametersManager parametersManager,
								 Characteristics characteristics) {
		this.cameraConnection = cameraConnection;
		this.surfaceReader = surfaceReader;
		this.textureManager = textureManager;
		this.parametersManager = parametersManager;
		this.characteristics = characteristics;
	}

	public CaptureRequest createPreviewRequest() throws CameraAccessException {

		CameraDevice camera = cameraConnection.getCamera();
		Surface surface = textureManager.getSurface();
		Flash flash = parametersManager.getParameters().getValue(FLASH);

		return CaptureRequestBuilder
				.create(camera, CameraDevice.TEMPLATE_PREVIEW)
				.into(surface)
				.flash(flash)
				.build();
	}

	/**
	 * Creates a request responsible to trigger an auto focus request.
	 *
	 * @return The camera request.
	 * @throws CameraAccessException If the camera device has been disconnected.
	 */
	public CaptureRequest createLockRequest() throws CameraAccessException {

		CameraDevice camera = cameraConnection.getCamera();
		Surface surface = textureManager.getSurface();
		Flash flash = parametersManager.getParameters().getValue(FLASH);
		boolean triggerAutoExposure = !characteristics.isLegacyDevice();

		return CaptureRequestBuilder
				.create(camera, CameraDevice.TEMPLATE_STILL_CAPTURE)
				.into(surface)
				.flash(flash)
				.triggerAutoFocus(true)
				.triggerPrecaptureExposure(triggerAutoExposure)
				.build();

	}

	/**
	 * Creates a request responsible to trigger a precapturing (auto-exposure) request.
	 *
	 * @return The camera request.
	 * @throws CameraAccessException If the camera device has been disconnected.
	 */
	public CaptureRequest createPrecaptureRequest() throws CameraAccessException {

		CameraDevice camera = cameraConnection.getCamera();
		Surface surface = textureManager.getSurface();

		Parameters parameters = parametersManager.getParameters();
		Flash flash = parameters.getValue(FLASH);
		FocusMode focus = parameters.getValue(FOCUS_MODE);

		boolean triggerPrecaptureExposure = !characteristics.isLegacyDevice();

		return CaptureRequestBuilder
				.create(camera, CameraDevice.TEMPLATE_STILL_CAPTURE)
				.into(surface)
				.triggerPrecaptureExposure(triggerPrecaptureExposure)
				.flash(flash)
				.focus(focus)
				.setExposureMode(true)
				.build();
	}

	/**
	 * Creates a request responsible to take a still image.
	 *
	 * @return The camera request.
	 * @throws CameraAccessException If the camera device has been disconnected.
	 */
	public CaptureRequest createCaptureRequest(Integer sensorOrientation) throws CameraAccessException {

		CameraDevice camera = cameraConnection.getCamera();
		Surface surface = surfaceReader.getSurface();

		Parameters parameters = parametersManager.getParameters();
		Flash flash = parameters.getValue(FLASH);
		FocusMode focus = parameters.getValue(FOCUS_MODE);

		return CaptureRequestBuilder
				.create(camera, CameraDevice.TEMPLATE_STILL_CAPTURE)
				.into(surface)
				.cancelPrecaptureExposure(true)
				.flash(flash)
				.focus(focus)
				.setExposureMode(true)
				.sensorOrientation(sensorOrientation)
				.build();
	}

}
