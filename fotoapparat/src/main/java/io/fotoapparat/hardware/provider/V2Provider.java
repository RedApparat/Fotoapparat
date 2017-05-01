package io.fotoapparat.hardware.provider;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.v2.Camera2;
import io.fotoapparat.hardware.v2.capabilities.CapabilitiesFactory;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.lens.executors.CaptureExecutor;
import io.fotoapparat.hardware.v2.lens.executors.ExposureGatheringExecutor;
import io.fotoapparat.hardware.v2.lens.executors.FocusExecutor;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.hardware.v2.parameters.RendererParametersProvider;
import io.fotoapparat.hardware.v2.selection.CameraSelector;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.hardware.v2.stream.PreviewStream2;
import io.fotoapparat.hardware.v2.surface.ContinuousSurfaceReader;
import io.fotoapparat.hardware.v2.surface.StillSurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;
import io.fotoapparat.log.Logger;

/**
 * Always provides {@link Camera2}.
 */
public class V2Provider implements CameraProvider {

    private final Context context;

    public V2Provider(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public CameraDevice get(Logger logger) {
        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        AvailableLensPositionsProvider availableLensPositionsProvider = new V2AvailableLensPositionProvider(
                context);

        CameraSelector cameraSelector = new CameraSelector(manager);

        CameraConnection cameraConnection = new CameraConnection(
                manager,
                cameraSelector
        );

        OrientationManager orientationManager = new OrientationManager(cameraConnection);

        ParametersProvider parametersProvider = new ParametersProvider();

        TextureManager textureManager = new TextureManager(
                orientationManager,
                parametersProvider
        );

        StillSurfaceReader stillSurfaceReader = new StillSurfaceReader(parametersProvider);
        ContinuousSurfaceReader continuousSurfaceReader = new ContinuousSurfaceReader(
                parametersProvider
        );

        CaptureRequestFactory captureRequestFactory = new CaptureRequestFactory(
                cameraConnection,
                stillSurfaceReader,
                continuousSurfaceReader,
                textureManager,
                parametersProvider
        );

        SessionManager sessionManager = new SessionManager(
                stillSurfaceReader,
                continuousSurfaceReader,
                cameraConnection,
                captureRequestFactory,
                textureManager
        );

        CapabilitiesFactory capabilitiesOperator = new CapabilitiesFactory(cameraConnection);

        PreviewStream2 previewStream = new PreviewStream2(
                continuousSurfaceReader,
                parametersProvider
        );

        RendererParametersProvider rendererParametersOperator = new RendererParametersProvider(
                parametersProvider,
                orientationManager
        );

        LensOperationsFactory lensOperationsFactory = new LensOperationsFactory(
                sessionManager,
                captureRequestFactory
        );

        FocusExecutor focusExecutor = new FocusExecutor(
                lensOperationsFactory
        );
        ExposureGatheringExecutor exposureGatheringExecutor = new ExposureGatheringExecutor(
                lensOperationsFactory
        );
        CaptureExecutor captureExecutor = new CaptureExecutor(
                lensOperationsFactory,
                stillSurfaceReader,
                orientationManager
        );

        return new Camera2(
                logger,
                cameraConnection,
                sessionManager,
                textureManager,
                orientationManager,
                parametersProvider,
                capabilitiesOperator,
                previewStream,
                rendererParametersOperator,
                focusExecutor,
                exposureGatheringExecutor,
                captureExecutor,
                availableLensPositionsProvider
        );
    }
}
