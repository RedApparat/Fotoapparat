package io.fotoapparat;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.fotoapparat.error.Callbacks;
import io.fotoapparat.error.CameraErrorCallback;
import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.OrientationSensor;
import io.fotoapparat.hardware.orientation.RotationListener;
import io.fotoapparat.hardware.orientation.ScreenOrientationProvider;
import io.fotoapparat.parameter.provider.CapabilitiesProvider;
import io.fotoapparat.parameter.provider.CurrentParametersProvider;
import io.fotoapparat.parameter.provider.InitialParametersProvider;
import io.fotoapparat.parameter.provider.InitialParametersValidator;
import io.fotoapparat.parameter.update.UpdateRequest;
import io.fotoapparat.result.CapabilitiesResult;
import io.fotoapparat.result.FocusResult;
import io.fotoapparat.result.ParametersResult;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.routine.CheckAvailabilityRoutine;
import io.fotoapparat.routine.ConfigurePreviewStreamRoutine;
import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.routine.StopCameraRoutine;
import io.fotoapparat.routine.UpdateOrientationRoutine;
import io.fotoapparat.routine.focus.AutoFocusRoutine;
import io.fotoapparat.routine.parameter.UpdateParametersRoutine;
import io.fotoapparat.routine.picture.TakePictureRoutine;
import io.fotoapparat.routine.zoom.UpdateZoomLevelRoutine;

/**
 * Camera. Takes pictures.
 */
public class Fotoapparat {

    private static final Executor SERIAL_EXECUTOR = Executors.newSingleThreadExecutor();

    private final StartCameraRoutine startCameraRoutine;
    private final StopCameraRoutine stopCameraRoutine;
    private final UpdateOrientationRoutine updateOrientationRoutine;
    private final ConfigurePreviewStreamRoutine configurePreviewStreamRoutine;
    private final CapabilitiesProvider capabilitiesProvider;
    private final CurrentParametersProvider currentParametersProvider;
    private final TakePictureRoutine takePictureRoutine;
    private final AutoFocusRoutine autoFocusRoutine;
    private final CheckAvailabilityRoutine checkAvailabilityRoutine;
    private final UpdateParametersRoutine updateParametersRoutine;
    private final UpdateZoomLevelRoutine updateZoomLevelRoutine;
    private final Executor executor;

    private boolean started = false;

    Fotoapparat(StartCameraRoutine startCameraRoutine,
                StopCameraRoutine stopCameraRoutine,
                UpdateOrientationRoutine updateOrientationRoutine,
                ConfigurePreviewStreamRoutine configurePreviewStreamRoutine,
                CapabilitiesProvider capabilitiesProvider,
                CurrentParametersProvider parametersProvider,
                TakePictureRoutine takePictureRoutine,
                AutoFocusRoutine autoFocusRoutine,
                CheckAvailabilityRoutine checkAvailabilityRoutine,
                UpdateParametersRoutine updateParametersRoutine,
                UpdateZoomLevelRoutine updateZoomLevelRoutine,
                Executor executor) {
        this.startCameraRoutine = startCameraRoutine;
        this.stopCameraRoutine = stopCameraRoutine;
        this.updateOrientationRoutine = updateOrientationRoutine;
        this.configurePreviewStreamRoutine = configurePreviewStreamRoutine;
        this.capabilitiesProvider = capabilitiesProvider;
        this.currentParametersProvider = parametersProvider;
        this.takePictureRoutine = takePictureRoutine;
        this.autoFocusRoutine = autoFocusRoutine;
        this.checkAvailabilityRoutine = checkAvailabilityRoutine;
        this.updateParametersRoutine = updateParametersRoutine;
        this.updateZoomLevelRoutine = updateZoomLevelRoutine;
        this.executor = executor;
    }

    public static FotoapparatBuilder with(Context context) {
        if (context == null) {
            throw new IllegalStateException("Context is null.");
        }

        return new FotoapparatBuilder(context);
    }

    static Fotoapparat create(FotoapparatBuilder builder) {
        CameraErrorCallback cameraErrorCallback = Callbacks.onMainThread(
                builder.cameraErrorCallback
        );

        CameraDevice cameraDevice = builder.cameraProvider.get(builder.logger);

        ScreenOrientationProvider screenOrientationProvider = new ScreenOrientationProvider(builder.context);
        RotationListener rotationListener = new RotationListener(builder.context);

        InitialParametersValidator parametersValidator = new InitialParametersValidator();
        InitialParametersProvider initialParametersProvider = new InitialParametersProvider(
                cameraDevice,
                builder.photoSizeSelector,
                builder.previewSizeSelector,
                builder.focusModeSelector,
                builder.flashSelector,
                builder.previewFpsRangeSelector,
                builder.sensorSensitivitySelector,
                builder.jpegQuality,
                parametersValidator
        );

        StartCameraRoutine startCameraRoutine = new StartCameraRoutine(
                cameraDevice,
                builder.renderer,
                builder.scaleType,
                builder.lensPositionSelector,
                screenOrientationProvider,
                initialParametersProvider,
                cameraErrorCallback
        );

        StopCameraRoutine stopCameraRoutine = new StopCameraRoutine(cameraDevice);

        OrientationSensor orientationSensor = new OrientationSensor(
                rotationListener,
                screenOrientationProvider
        );

        UpdateOrientationRoutine updateOrientationRoutine = new UpdateOrientationRoutine(
                cameraDevice,
                orientationSensor,
                SERIAL_EXECUTOR,
                builder.logger
        );

        ConfigurePreviewStreamRoutine configurePreviewStreamRoutine = new ConfigurePreviewStreamRoutine(
                cameraDevice,
                builder.frameProcessor
        );

        CapabilitiesProvider capabilitiesProvider = new CapabilitiesProvider(
                cameraDevice,
                SERIAL_EXECUTOR
        );

        CurrentParametersProvider currentParametersProvider = new CurrentParametersProvider(
                cameraDevice,
                SERIAL_EXECUTOR
        );

        TakePictureRoutine takePictureRoutine = new TakePictureRoutine(
                cameraDevice,
                SERIAL_EXECUTOR
        );

        AutoFocusRoutine autoFocusRoutine = new AutoFocusRoutine(
                cameraDevice,
                SERIAL_EXECUTOR
        );

        CheckAvailabilityRoutine checkAvailabilityRoutine = new CheckAvailabilityRoutine(
                cameraDevice,
                builder.lensPositionSelector
        );

        UpdateParametersRoutine updateParametersRoutine = new UpdateParametersRoutine(
                cameraDevice
        );

        UpdateZoomLevelRoutine updateZoomLevelRoutine = new UpdateZoomLevelRoutine(
                cameraDevice
        );

        return new Fotoapparat(
                startCameraRoutine,
                stopCameraRoutine,
                updateOrientationRoutine,
                configurePreviewStreamRoutine,
                capabilitiesProvider,
                currentParametersProvider,
                takePictureRoutine,
                autoFocusRoutine,
                checkAvailabilityRoutine,
                updateParametersRoutine,
                updateZoomLevelRoutine,
                SERIAL_EXECUTOR
        );
    }

    /**
     * @return {@code true} if camera for this {@link Fotoapparat} is available. {@code false} if
     * it is not available.
     */
    public boolean isAvailable() {
        return checkAvailabilityRoutine.isAvailable();
    }

    /**
     * Provides camera capabilities asynchronously, returns immediately.
     *
     * @return {@link CapabilitiesResult} which will deliver result asynchronously.
     */
    public CapabilitiesResult getCapabilities() {
        ensureStarted();

        return capabilitiesProvider.getCapabilities();
    }

    /**
     * Provides current camera parameters asynchronously, returns immediately.
     *
     * @return {@link ParametersResult} which will deliver result asynchronously.
     */
    public ParametersResult getCurrentParameters() {
        ensureStarted();

        return currentParametersProvider.getParameters();
    }

    /**
     * Takes picture. Returns immediately.
     *
     * @return {@link PhotoResult} which will deliver result asynchronously.
     */
    public PhotoResult takePicture() {
        ensureStarted();

        return takePictureRoutine.takePicture();
    }

    /**
     * Performs auto focus. If it is not available or not enabled, does nothing.
     */
    public Fotoapparat autoFocus() {
        focus();

        return this;
    }

    /**
     * Attempts to focus the camera asynchronously.
     *
     * @return the pending result of focus operation which will deliver result asynchronously.
     */
    public PendingResult<FocusResult> focus() {
        ensureStarted();

        return autoFocusRoutine.autoFocus();
    }

    /**
     * Asynchronously updates parameters of the camera. Must be called only after {@link #start()}.
     */
    public void updateParameters(@NonNull final UpdateRequest updateRequest) {
        ensureStarted();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                updateParametersRoutine.updateParameters(updateRequest);
            }
        });
    }

    /**
     * Asynchronously updates zoom level of the camera. Must be called only after {@link #start()}.
     * <p>
     * If zoom is not supported by the device - does nothing.
     *
     * @param zoomLevel zoom level of the camera. A value between 0 and 1.
     */
    public void setZoom(@FloatRange(from = 0f, to = 1f) final float zoomLevel) {
        ensureStarted();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                updateZoomLevelRoutine.updateZoomLevel(zoomLevel);
            }
        });
    }

    /**
     * Starts camera.
     *
     * @throws IllegalStateException if camera was already started.
     */
    public void start() {
        ensureNotStarted();
        started = true;

        startCamera();
        configurePreviewStream();
        updateOrientationRoutine.start();
    }

    /**
     * Stops camera.
     *
     * @throws IllegalStateException if camera is not started.
     */
    public void stop() {
        ensureStarted();
        started = false;

        updateOrientationRoutine.stop();
        stopCamera();
    }

    private void startCamera() {
        executor.execute(
                startCameraRoutine
        );
    }

    private void stopCamera() {
        executor.execute(
                stopCameraRoutine
        );
    }

    private void configurePreviewStream() {
        executor.execute(
                configurePreviewStreamRoutine
        );
    }

    private void ensureStarted() {
        if (!started) {
            throw new IllegalStateException("Camera is not started!");
        }
    }

    private void ensureNotStarted() {
        if (started) {
            throw new IllegalStateException("Camera is already started!");
        }
    }
}
