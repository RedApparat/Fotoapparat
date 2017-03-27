package io.fotoapparat.routine;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

/**
 * Opens camera and starts preview.
 */
public class StartCameraRoutine implements Runnable {

	private final CameraDevice cameraDevice;
	private final CameraRenderer cameraRenderer;
	private final SelectorFunction<LensPosition> lensPositionSelector;

	public StartCameraRoutine(CameraDevice cameraDevice,
							  CameraRenderer cameraRenderer,
							  SelectorFunction<LensPosition> lensPositionSelector) {
		this.cameraDevice = cameraDevice;
		this.cameraRenderer = cameraRenderer;
		this.lensPositionSelector = lensPositionSelector;
	}

	@Override
	public void run() {
		LensPosition lensPosition = lensPositionSelector.select(
				cameraDevice.getSupportedLensPositions()
		);

		cameraDevice.open(lensPosition);
		cameraRenderer.attachCamera(cameraDevice);
		cameraDevice.startPreview();
	}

}
