package io.fotoapparat.routine;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.OrientationSensor;
import io.fotoapparat.log.Logger;

/**
 * Observes current device orientation and updates {@link CameraDevice} accordingly.
 */
public class UpdateOrientationRoutine implements OrientationSensor.Listener {

    private final CameraDevice cameraDevice;
    private final OrientationSensor orientationSensor;
    private final Executor cameraExecutor;
    private final Logger logger;

    public UpdateOrientationRoutine(CameraDevice cameraDevice,
                                    OrientationSensor orientationSensor,
                                    Executor cameraExecutor,
                                    Logger logger) {
        this.cameraDevice = cameraDevice;
        this.orientationSensor = orientationSensor;
        this.cameraExecutor = cameraExecutor;
        this.logger = logger;
    }

    /**
     * Starts routine.
     */
    public void start() {
        orientationSensor.start(this);
    }

    /**
     * Stops routine.
     */
    public void stop() {
        orientationSensor.stop();
    }

    @Override
    public void onOrientationChanged(final int degrees) {
        cameraExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    cameraDevice.setDisplayOrientation(degrees);
                } catch (RuntimeException e) {
                    logger.log("Failed to perform cameraDevice.setDisplayOrientation(" + degrees + ") e: " + e.getMessage());
                }
            }
        });
    }
}
