package io.fotoapparat.hardware.provider;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.v2.Camera2;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.capabilities.CapabilitiesFactory;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.lens.executors.CaptureOperatorImpl;
import io.fotoapparat.hardware.v2.lens.executors.ExposureGatheringExecutor;
import io.fotoapparat.hardware.v2.lens.executors.FocusExecutor;
import io.fotoapparat.hardware.v2.lens.operations.LensOperationsFactory;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.hardware.v2.parameters.RendererParametersProvider;
import io.fotoapparat.hardware.v2.readers.ContinuousSurfaceReader;
import io.fotoapparat.hardware.v2.readers.StillSurfaceReader;
import io.fotoapparat.hardware.v2.selection.CameraSelector;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.hardware.v2.session.SessionProvider;
import io.fotoapparat.hardware.v2.stream.PreviewStream2;
import io.fotoapparat.hardware.v2.surface.TextureManager;
import io.fotoapparat.log.Logger;

/**
 * Always provides {@link Camera2}.
 */
public class V2Provider implements CameraProvider {

    private static final CameraThread CAMERA_THREAD = new CameraThread();
    private final Context context;

    public V2Provider(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public CameraDevice get(Logger logger) {

        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        AvailableLensPositionsProvider availableLensPositionsProvider = new V2AvailableLensPositionProvider(
                context
        );

        CameraSelector cameraSelector = new CameraSelector(manager);

        CameraConnection cameraConnection = new CameraConnection(
                cameraSelector,
                manager,
                CAMERA_THREAD
        );

        ParametersProvider parametersProvider = new ParametersProvider();

        OrientationManager orientationManager = new OrientationManager(
                cameraConnection
        );

        StillSurfaceReader stillSurfaceReader = new StillSurfaceReader(
                parametersProvider,
                CAMERA_THREAD
        );
        ContinuousSurfaceReader continuousSurfaceReader = new ContinuousSurfaceReader(
                parametersProvider,
                CAMERA_THREAD
        );
        TextureManager textureManager = new TextureManager(
                orientationManager,
                parametersProvider
        );

        CaptureRequestFactory captureRequestFactory = new CaptureRequestFactory(
                cameraConnection,
                stillSurfaceReader,
                textureManager,
                parametersProvider
        );

        SessionProvider sessionProvider = new SessionProvider(
                stillSurfaceReader,
                cameraConnection,
                captureRequestFactory,
                textureManager,
                CAMERA_THREAD
        );

        SessionManager sessionManager = new SessionManager(
                cameraConnection,
                sessionProvider
        );

        CapabilitiesFactory capabilitiesOperator = new CapabilitiesFactory(cameraConnection);

        PreviewStream2 previewStream = new PreviewStream2(
                continuousSurfaceReader,
                parametersProvider,
                logger
        );

        RendererParametersProvider rendererParametersOperator = new RendererParametersProvider(
                parametersProvider,
                orientationManager
        );

        LensOperationsFactory lensOperationsFactory = new LensOperationsFactory(
                sessionManager,
                captureRequestFactory,
                CAMERA_THREAD
        );

        FocusExecutor focusExecutor = new FocusExecutor(
                parametersProvider,
                lensOperationsFactory
        );
        ExposureGatheringExecutor exposureGatheringExecutor = new ExposureGatheringExecutor(
                lensOperationsFactory
        );
        CaptureOperatorImpl captureExecutor = new CaptureOperatorImpl(
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
