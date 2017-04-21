package io.fotoapparat.parameter.provider;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.CapabilitiesResult;

/**
 * Provides camera capabilities.
 */
public class CapabilitiesProvider {

    private final CameraDevice cameraDevice;
    private final Executor cameraExecutor;

    public CapabilitiesProvider(CameraDevice cameraDevice,
                                Executor cameraExecutor) {
        this.cameraDevice = cameraDevice;
        this.cameraExecutor = cameraExecutor;
    }

    /**
     * Provides camera capabilities asynchronously, returns immediately.
     *
     * @return {@link CapabilitiesResult} which will deliver result asynchronously.
     */
    public CapabilitiesResult getCapabilities() {
        GetCapabilitiesTask getCapabilitiesTask = new GetCapabilitiesTask(cameraDevice);
        cameraExecutor.execute(getCapabilitiesTask);

        return CapabilitiesResult.fromFuture(getCapabilitiesTask);
    }
}
