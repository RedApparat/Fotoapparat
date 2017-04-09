package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
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
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CaptureRequestFactory {

	private final SurfaceReader surfaceReader;
	private final TextureManager textureManager;
	private final RequestFieldsProvider requestFieldsProvider;
	private final Characteristics characteristics;
	private CameraConnection cameraConnection;

	public CaptureRequestFactory(CameraConnection cameraConnection,
								 SurfaceReader surfaceReader,
								 TextureManager textureManager,
								 RequestFieldsProvider requestFieldsProvider,
								 Characteristics characteristics) {
		this.cameraConnection = cameraConnection;
		this.surfaceReader = surfaceReader;
		this.textureManager = textureManager;
		this.requestFieldsProvider = requestFieldsProvider;
		this.characteristics = characteristics;
	}

	private void setCapturingModes(CaptureRequest.Builder requestBuilder) {
		if (!isFixedFocusLens()) {
			requestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
					requestFieldsProvider.getFocusMode());
		}
		requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
				requestFieldsProvider.getAutoExposureMode());


		Integer flashFiringMode = requestFieldsProvider.getFlashFiringMode();
		if (flashFiringMode != null) {
			requestBuilder.set(CaptureRequest.FLASH_MODE, flashFiringMode);
		}
	}

	private void setAutoExposureTrigger(CaptureRequest.Builder requestBuilder) {
		if (isLegacyDevice()) {
			return;
		}
		requestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
				CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_START
		);
	}

	private boolean isFixedFocusLens() {
		Float minimumFocusDistance = characteristics
				.getCameraCharacteristics()
				.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);

		return minimumFocusDistance != null && minimumFocusDistance != 0;
	}

	@SuppressWarnings("ConstantConditions")
	private boolean isLegacyDevice() {
		Integer hardwareLevel = characteristics
				.getCameraCharacteristics()
				.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);

		return hardwareLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY;
	}

	private CaptureRequest.Builder createCaptureRequestBuilder() throws CameraAccessException {
		return cameraConnection
				.getCamera()
				.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
	}

	public CaptureRequest createLockRequest() throws CameraAccessException {

		CaptureRequest.Builder requestBuilder = createCaptureRequestBuilder();
		requestBuilder.addTarget(new Surface(textureManager.getSurfaceTexture()));

		requestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
				CameraMetadata.CONTROL_AF_TRIGGER_START
		);

		setAutoExposureTrigger(requestBuilder);
		setCapturingModes(requestBuilder);

		return requestBuilder.build();
	}

	public CaptureRequest createPrecaptureRequest() throws
			CameraAccessException {

		CaptureRequest.Builder requestBuilder = createCaptureRequestBuilder();
		requestBuilder.addTarget(new Surface(textureManager.getSurfaceTexture()));

		setAutoExposureTrigger(requestBuilder);
		setCapturingModes(requestBuilder);

		return requestBuilder.build();
	}

	public CaptureRequest createCaptureRequest(Integer sensorOrientation)
			throws CameraAccessException {

		CaptureRequest.Builder requestBuilder = createCaptureRequestBuilder();
		requestBuilder.addTarget(surfaceReader.getSurface());

		setCapturingModes(requestBuilder);
		requestBuilder.set(CaptureRequest.JPEG_ORIENTATION, sensorOrientation);

		requestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
				CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_CANCEL
		);

		return requestBuilder.build();
	}

}
