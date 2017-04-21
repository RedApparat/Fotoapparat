package io.fotoapparat.hardware.provider;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.log.Logger;

/**
 * Abstraction for providing camera.
 */
public interface CameraProvider {

    /**
     * @return a {@link CameraDevice}.
     */
    CameraDevice get(Logger logger);
}
