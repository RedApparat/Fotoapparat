package io.fotoapparat.parameter.provider;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;

/**
 * Gets the camera {@link Capabilities}.
 */
public class GetCapabilitiesTask extends FutureTask<Capabilities> {

    public GetCapabilitiesTask(final CameraDevice cameraDevice) {
        super(new Callable<Capabilities>() {
            @Override
            public Capabilities call() throws Exception {
                return cameraDevice.getCapabilities();
            }
        });
    }

}