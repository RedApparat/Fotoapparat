package io.fotoapparat.routine;

import java.util.Collection;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Checks whether {@link LensPosition} provided by {@link SelectorFunction} is available or not.
 */
public class CheckAvailabilityRoutine {

    private final CameraDevice cameraDevice;
    private final SelectorFunction<Collection<LensPosition>, LensPosition> lensPositionSelector;

    public CheckAvailabilityRoutine(CameraDevice cameraDevice,
                                    SelectorFunction<Collection<LensPosition>, LensPosition> lensPositionSelector) {
        this.cameraDevice = cameraDevice;
        this.lensPositionSelector = lensPositionSelector;
    }

    /**
     * @return {@code true} if selected lens position is available. {@code false} if it is not
     * available.
     */
    public boolean isAvailable() {
        return selectedLensPosition() != null;
    }

    private LensPosition selectedLensPosition() {
        return lensPositionSelector.select(cameraDevice.getAvailableLensPositions());
    }

}
