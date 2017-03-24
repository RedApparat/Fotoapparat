package io.fotoapparat.hardware;

/**
 * Abstraction for providing camera.
 */
public interface CameraProvider {

    /**
     * Returns a {@link CameraDevice} for the given {@link Parameters} if
     * available from the device.
     *
     * @param parameters the selected parameters which will determine which {@link CameraDevice}
     *                   and with which parameters to use the {@link CameraDevice}.
     * @return a {@link CameraDevice} object if the options matches the parameters.
     */
    CameraDevice get(Parameters parameters);
}
