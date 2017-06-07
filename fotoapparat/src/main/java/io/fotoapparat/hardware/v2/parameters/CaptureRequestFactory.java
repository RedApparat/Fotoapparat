package io.fotoapparat.hardware.v2.parameters;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.readers.StillSurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;

/**
 * Creates {@link CaptureRequest}s for a {@link android.hardware.camera2.CameraCaptureSession}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CaptureRequestFactory {

    private final StillSurfaceReader surfaceReader;
    private final TextureManager textureManager;
    private final ParametersProvider parametersProvider;
    private final CameraConnection cameraConnection;

    public CaptureRequestFactory(CameraConnection cameraConnection,
                                 StillSurfaceReader surfaceReader,
                                 TextureManager textureManager,
                                 ParametersProvider parametersProvider) {
        this.cameraConnection = cameraConnection;
        this.surfaceReader = surfaceReader;
        this.textureManager = textureManager;
        this.parametersProvider = parametersProvider;
    }

    /**
     * Creates a request for a window preview.
     *
     * @return The camera request.
     * @throws CameraAccessException If the camera device has been disconnected.
     */
    public CaptureRequest createPreviewRequest() throws CameraAccessException {

        CameraDevice camera = cameraConnection.getCamera();
        Surface viewSurface = textureManager.getSurface();
        Flash flash = parametersProvider.getFlash();
        Integer sensorSensitivity = parametersProvider.getSensorSensitivity();

        return CaptureRequestBuilder
                .create(camera, CameraDevice.TEMPLATE_PREVIEW)
                .into(viewSurface)
                .flash(flash)
                .sensorSensitivity(sensorSensitivity)
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
        Flash flash = parametersProvider.getFlash();
        boolean triggerAutoExposure = !cameraConnection.getCharacteristics().isLegacyDevice();
        Integer sensorSensitivity = parametersProvider.getSensorSensitivity();

        return CaptureRequestBuilder
                .create(camera, CameraDevice.TEMPLATE_STILL_CAPTURE)
                .into(surface)
                .flash(flash)
                .triggerAutoFocus(true)
                .triggerPrecaptureExposure(triggerAutoExposure)
                .sensorSensitivity(sensorSensitivity)
                .build();
    }

    /**
     * Creates a request responsible to trigger a precapturing (auto-exposure) request.
     *
     * @return The camera request.
     * @throws CameraAccessException If the camera device has been disconnected.
     */
    public CaptureRequest createExposureGatheringRequest() throws CameraAccessException {

        CameraDevice camera = cameraConnection.getCamera();
        Surface surface = textureManager.getSurface();
        Flash flash = parametersProvider.getFlash();
        FocusMode focus = parametersProvider.getFocus();
        Integer sensorSensitivity = parametersProvider.getSensorSensitivity();

        boolean triggerPrecaptureExposure = !cameraConnection.getCharacteristics().isLegacyDevice();

        return CaptureRequestBuilder
                .create(camera, CameraDevice.TEMPLATE_STILL_CAPTURE)
                .into(surface)
                .triggerPrecaptureExposure(triggerPrecaptureExposure)
                .flash(flash)
                .focus(focus)
                .setExposureMode(true)
                .sensorSensitivity(sensorSensitivity)
                .build();
    }

    /**
     * @return a request to take a photo.
     * @throws CameraAccessException if the camera device has been disconnected.
     */
    public CaptureRequest createCaptureRequest() throws CameraAccessException {

        CameraDevice camera = cameraConnection.getCamera();
        Surface surface = surfaceReader.getSurface();
        Flash flash = parametersProvider.getFlash();
        FocusMode focus = parametersProvider.getFocus();
        Integer sensorSensitivity = parametersProvider.getSensorSensitivity();

        return CaptureRequestBuilder
                .create(camera, CameraDevice.TEMPLATE_STILL_CAPTURE)
                .into(surface)
                .cancelPrecaptureExposure(true)
                .flash(flash)
                .focus(focus)
                .sensorSensitivity(sensorSensitivity)
                .setExposureMode(true)
                .build();
    }

}
