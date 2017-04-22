package io.fotoapparat.hardware.operators;

import io.fotoapparat.hardware.Capabilities;

/**
 * An interface which indicates that the class can
 * provide the camera capabilities.
 */
public interface CapabilitiesOperator {

    /**
     * Returns the {@link Capabilities} of the opened camera.
     *
     * @return The {@link Capabilities} of the camera.
     */
    Capabilities getCapabilities();
}
