package io.fotoapparat.hardware.v2.captor.parameters;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.surface.SurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;

/**
 * Creates {@link CaptureRequest}s for a {@link android.hardware.camera2.CameraCaptureSession}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CaptureRequestFactory {

	private final SurfaceReader surfaceReader;
	private final TextureManager textureManager;
	private final CaptureParametersProvider captureParametersProvider;
	private final Characteristics characteristics;
	private CameraConnection cameraConnection;

	public CaptureRequestFactory(CameraConnection cameraConnection,
								 SurfaceReader surfaceReader,
								 TextureManager textureManager,
								 CaptureParametersProvider captureParametersProvider,
								 Characteristics characteristics) {
		this.cameraConnection = cameraConnection;
		this.surfaceReader = surfaceReader;
		this.textureManager = textureManager;
		this.captureParametersProvider = captureParametersProvider;
		this.characteristics = characteristics;
	}

	/**
	 * Creates a request responsible to trigger an auto focus request.
	 *
	 * @return The camera request.
	 * @throws CameraAccessException If the camera device has been disconnected.
	 */
	public CaptureRequest createLockRequest() throws CameraAccessException {

		CaptureRequest.Builder requestBuilder = createCaptureRequestBuilder();
		requestBuilder.addTarget(new Surface(textureManager.getSurfaceTexture()));

		requestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
				CameraMetadata.CONTROL_AF_TRIGGER_START
		);

		setAutoExposureTrigger(requestBuilder);

		return requestBuilder.build();
	}

	/**
	 * Creates a request responsible to trigger a precapturing (auto-exposure) request.
	 *
	 * @return The camera request.
	 * @throws CameraAccessException If the camera device has been disconnected.
	 */
	public CaptureRequest createPrecaptureRequest() throws CameraAccessException {

		CaptureRequest.Builder requestBuilder = createCaptureRequestBuilder();
		requestBuilder.addTarget(new Surface(textureManager.getSurfaceTexture()));

		setAutoExposureTrigger(requestBuilder);
		setCapturingModes(requestBuilder);

		return requestBuilder.build();
	}

	/**
	 * Creates a request responsible to take a still image.
	 *
	 * @return The camera request.
	 * @throws CameraAccessException If the camera device has been disconnected.
	 */
	public CaptureRequest createCaptureRequest(Integer sensorOrientation) throws CameraAccessException {

		CaptureRequest.Builder requestBuilder = createCaptureRequestBuilder();
		requestBuilder.addTarget(surfaceReader.getSurface());

		setCapturingModes(requestBuilder);
		requestBuilder.set(CaptureRequest.JPEG_ORIENTATION, sensorOrientation);

		requestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
				CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_CANCEL
		);

		return requestBuilder.build();
	}

	private void setCapturingModes(CaptureRequest.Builder requestBuilder) {
		requestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);

		if (!characteristics.isFixedFocusLens()) {
			requestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
					captureParametersProvider.getFocusMode());
		}

		requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
				captureParametersProvider.getAutoExposureMode());

		Integer flashFiringMode = captureParametersProvider.getFlashFiringMode();
		if (flashFiringMode != null) {
			requestBuilder.set(CaptureRequest.FLASH_MODE, flashFiringMode);
		}
	}

	private void setAutoExposureTrigger(CaptureRequest.Builder requestBuilder) {
		if (!characteristics.isLegacyDevice()) {
			requestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
					CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_START
			);
		}
	}

	private CaptureRequest.Builder createCaptureRequestBuilder() throws CameraAccessException {
		CaptureRequest.Builder captureRequest = cameraConnection
				.getCamera()
				.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);

		captureRequest.set(CaptureRequest.CONTROL_CAPTURE_INTENT,
				CameraMetadata.CONTROL_CAPTURE_INTENT_STILL_CAPTURE);

		return captureRequest;
	}

}
