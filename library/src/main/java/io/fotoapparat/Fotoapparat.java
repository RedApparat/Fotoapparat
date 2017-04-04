package io.fotoapparat;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.orientation.OrientationSensor;
import io.fotoapparat.hardware.orientation.RotationListener;
import io.fotoapparat.hardware.orientation.ScreenOrientationProvider;
import io.fotoapparat.parameter.provider.InitialParametersProvider;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.routine.StopCameraRoutine;
import io.fotoapparat.routine.TakePictureRoutine;
import io.fotoapparat.routine.UpdateOrientationRoutine;

/**
 * Camera. Takes pictures.
 */
public class Fotoapparat {

    private static final Executor SERIAL_EXECUTOR = Executors.newSingleThreadExecutor();

    private final StartCameraRoutine startCameraRoutine;
    private final StopCameraRoutine stopCameraRoutine;
    private final UpdateOrientationRoutine updateOrientationRoutine;
    private final TakePictureRoutine takePictureRoutine;
    private final Executor executor;

    private boolean started = false;

    Fotoapparat(StartCameraRoutine startCameraRoutine,
                StopCameraRoutine stopCameraRoutine,
                UpdateOrientationRoutine updateOrientationRoutine,
                TakePictureRoutine takePictureRoutine,
                Executor executor) {
        this.startCameraRoutine = startCameraRoutine;
        this.stopCameraRoutine = stopCameraRoutine;
        this.updateOrientationRoutine = updateOrientationRoutine;
        this.takePictureRoutine = takePictureRoutine;
        this.executor = executor;
    }

    public static FotoapparatBuilder with(Context context) {
        return new FotoapparatBuilder(context);
    }

    static Fotoapparat create(FotoapparatBuilder builder) {

        CameraDevice cameraDevice = builder.cameraProvider.get(builder.logger);
        ScreenOrientationProvider screenOrientationProvider = new ScreenOrientationProvider(builder.context);
        RotationListener rotationListener = new RotationListener(builder.context);
        InitialParametersProvider initialParametersProvider = new InitialParametersProvider(
                cameraDevice,
                builder.focusModeSelector
        );

        StartCameraRoutine startCameraRoutine = new StartCameraRoutine(
                builder.availableLensPositionsProvider,
                cameraDevice,
                builder.renderer,
                builder.lensPositionSelector,
                screenOrientationProvider,
                initialParametersProvider
        );

        StopCameraRoutine stopCameraRoutine = new StopCameraRoutine(cameraDevice);

        OrientationSensor orientationSensor = new OrientationSensor(
                rotationListener,
                screenOrientationProvider
        );

        UpdateOrientationRoutine updateOrientationRoutine = new UpdateOrientationRoutine(
                cameraDevice,
                orientationSensor,
                SERIAL_EXECUTOR
        );

        TakePictureRoutine takePictureRoutine = new TakePictureRoutine(
                cameraDevice,
                SERIAL_EXECUTOR
        );

        return new Fotoapparat(
                startCameraRoutine,
                stopCameraRoutine,
                updateOrientationRoutine,
                takePictureRoutine,
                SERIAL_EXECUTOR
        );
    }

    public Capabilities getCapabilities() {
        return null;
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
     * Starts camera.
     *
     * @throws IllegalStateException if camera was already started.
     */
    public void start() {
        ensureNotStarted();
        started = true;

        executor.execute(
                startCameraRoutine
        );
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

        executor.execute(
                stopCameraRoutine
        );
        updateOrientationRoutine.stop();
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
