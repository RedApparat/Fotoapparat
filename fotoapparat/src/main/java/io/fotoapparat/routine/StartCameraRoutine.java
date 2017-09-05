package io.fotoapparat.routine;

import io.fotoapparat.error.CameraErrorCallback;
import java.util.Collection;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.orientation.ScreenOrientationProvider;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.parameter.provider.InitialParametersProvider;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

/**
 * Opens camera and starts preview.
 */
public class StartCameraRoutine implements Runnable {

    private final CameraDevice cameraDevice;
    private final CameraRenderer cameraRenderer;
    private final ScaleType scaleType;
    private final SelectorFunction<Collection<LensPosition>, LensPosition> lensPositionSelector;
    private final ScreenOrientationProvider screenOrientationProvider;
    private final InitialParametersProvider initialParametersProvider;
    private final CameraErrorCallback cameraErrorCallback;

    public StartCameraRoutine(CameraDevice cameraDevice,
                              CameraRenderer cameraRenderer,
                              ScaleType scaleType,
                              SelectorFunction<Collection<LensPosition>, LensPosition> lensPositionSelector,
                              ScreenOrientationProvider screenOrientationProvider,
                              InitialParametersProvider initialParametersProvider,
                              CameraErrorCallback cameraErrorCallback) {
        this.cameraDevice = cameraDevice;
        this.cameraRenderer = cameraRenderer;
        this.scaleType = scaleType;
        this.lensPositionSelector = lensPositionSelector;
        this.screenOrientationProvider = screenOrientationProvider;
        this.initialParametersProvider = initialParametersProvider;
        this.cameraErrorCallback = cameraErrorCallback;
    }

    @Override
    public void run() {
        try {
            tryToStartCamera();
        } catch (CameraException e) {
            cameraErrorCallback.onError(e);
        }
    }

    private void tryToStartCamera() {
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
        cameraRenderer.setScaleType(scaleType);
        cameraRenderer.attachCamera(cameraDevice);
        cameraDevice.startPreview();
    }
}
