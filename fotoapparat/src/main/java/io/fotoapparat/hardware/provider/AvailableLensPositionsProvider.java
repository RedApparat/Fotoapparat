package io.fotoapparat.hardware.provider;

import java.util.List;

import io.fotoapparat.parameter.LensPosition;

/**
 * Provides list of {@link LensPosition} which are available on the device.
 */
public interface AvailableLensPositionsProvider {

    /**
     * @return list of {@link LensPosition} which are available on the device.
     */
    List<LensPosition> getAvailableLensPositions();

}
