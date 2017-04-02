package io.fotoapparat.routine;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.ScreenOrientationProvider;
import io.fotoapparat.hardware.provider.AvailableLensPositionsProvider;
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
	private final ScreenOrientationProvider screenOrientationProvider;

	public StartCameraRoutine(AvailableLensPositionsProvider availableLensPositionsProvider,
							  CameraDevice cameraDevice,
							  CameraRenderer cameraRenderer,
							  SelectorFunction<LensPosition> lensPositionSelector,
							  ScreenOrientationProvider screenOrientationProvider) {
		this.availableLensPositionsProvider = availableLensPositionsProvider;
		this.cameraDevice = cameraDevice;
		this.cameraRenderer = cameraRenderer;
		this.lensPositionSelector = lensPositionSelector;
		this.screenOrientationProvider = screenOrientationProvider;
	}

	@Override
	public void run() {
		LensPosition lensPosition = lensPositionSelector.select(
				availableLensPositionsProvider.getAvailableLensPositions()
		);

		cameraDevice.open(lensPosition);
		cameraRenderer.attachCamera(cameraDevice);
		cameraDevice.setDisplayOrientation(
				screenOrientationProvider.getScreenRotation()
		);
		cameraDevice.startPreview();
	}
}
