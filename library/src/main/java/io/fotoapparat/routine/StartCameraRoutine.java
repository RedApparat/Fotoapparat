package io.fotoapparat.routine;

import io.fotoapparat.hardware.provider.AvailableLensPositionsProvider;
import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

/**
 * Opens camera and starts preview.
 */
public class StartCameraRoutine implements Runnable {

	private final AvailableLensPositionsProvider availableLensPositionsProvider;
	private final CameraDevice cameraDevice;
	private final CameraRenderer cameraRenderer;
	private final SelectorFunction<LensPosition> lensPositionSelector;

	public StartCameraRoutine(AvailableLensPositionsProvider availableLensPositionsProvider,
							  CameraDevice cameraDevice,
							  CameraRenderer cameraRenderer,
							  SelectorFunction<LensPosition> lensPositionSelector) {
		this.availableLensPositionsProvider = availableLensPositionsProvider;
		this.cameraDevice = cameraDevice;
		this.cameraRenderer = cameraRenderer;
		this.lensPositionSelector = lensPositionSelector;
	}

	@Override
	public void run() {
		LensPosition lensPosition = lensPositionSelector.select(
				availableLensPositionsProvider.getAvailableLensPositions()
		);

		cameraDevice.open(lensPosition);
		cameraRenderer.attachCamera(cameraDevice);
		cameraDevice.startPreview();
	}

}
