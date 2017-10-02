package io.fotoapparat.parameter.provider;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Parameters;

/**
 * Gets the camera {@link Parameters}.
 */
public class GetCurrentParametersTask extends FutureTask<Parameters> {

    public GetCurrentParametersTask(final CameraDevice cameraDevice) {
        super(new Callable<Parameters>() {
            @Override
            public Parameters call() throws Exception {
                return cameraDevice.getCurrentParameters();
            }
        });
    }

}