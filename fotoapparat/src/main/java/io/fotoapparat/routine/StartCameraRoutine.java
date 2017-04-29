package io.fotoapparat.routine;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.ScreenOrientationProvider;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.provider.InitialParametersProvider;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

/**
 * Opens camera and starts preview.
 */
public class StartCameraRoutine implements Runnable {

	private final CameraDevice cameraDevice;
	private final CameraRenderer cameraRenderer;
	private final SelectorFunction<LensPosition> lensPositionSelector;
	private final ScreenOrientationProvider screenOrientationProvider;
	private final InitialParametersProvider initialParametersProvider;

	public StartCameraRoutine(CameraDevice cameraDevice,
							  CameraRenderer cameraRenderer,
							  SelectorFunction<LensPosition> lensPositionSelector,
							  ScreenOrientationProvider screenOrientationProvider,
							  InitialParametersProvider initialParametersProvider) {
		this.cameraDevice = cameraDevice;
		this.cameraRenderer = cameraRenderer;
		this.lensPositionSelector = lensPositionSelector;
		this.screenOrientationProvider = screenOrientationProvider;
		this.initialParametersProvider = initialParametersProvider;
	}

	@Override
	public void run() {
		LensPosition lensPosition = lensPositionSelector.select(
				cameraDevice.getAvailableLensPositions()
		);

		cameraDevice.open(lensPosition);
		cameraDevice.updateParameters(
				initialParametersProvider.initialParameters()
		);
		cameraDevice.setDisplayOrientation(
				screenOrientationProvider.getScreenRotation()
		);
		cameraRenderer.attachCamera(cameraDevice);
		cameraDevice.startPreview();
	}
}
