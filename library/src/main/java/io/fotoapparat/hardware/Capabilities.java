package io.fotoapparat.hardware;

import java.util.Set;

import io.fotoapparat.parameter.FocusMode;

/**
 * Capabilities of camera hardware.
 */
public class Capabilities {

    private final Set<FocusMode> focusModes;

    public Capabilities(Set<FocusMode> focusModes) {
        this.focusModes = focusModes;
    }

    /**
     * @return list of supported focus modes.
     */
    public Set<FocusMode> supportedFocusModes() {
        return focusModes;
    }

}
