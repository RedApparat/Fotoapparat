package io.fotoapparat.parameter.provider;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.ParametersResult;

/**
 * Provides camera current parameters.
 */
public class CurrentParametersProvider {

    private final CameraDevice cameraDevice;
    private final Executor cameraExecutor;

    public CurrentParametersProvider(CameraDevice cameraDevice,
                                     Executor cameraExecutor) {
        this.cameraDevice = cameraDevice;
        this.cameraExecutor = cameraExecutor;
    }

    /**
     * Provides camera current parameters asynchronously, returns immediately.
     *
     * @return {@link ParametersResult} which will deliver result asynchronously.
     */
    public ParametersResult getParameters() {
        GetCurrentParametersTask getParametersTask = new GetCurrentParametersTask(cameraDevice);
        cameraExecutor.execute(getParametersTask);

        return ParametersResult.fromFuture(getParametersTask);
    }
}
