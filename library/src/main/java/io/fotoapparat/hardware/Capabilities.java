package io.fotoapparat.hardware;

import java.util.List;

import io.fotoapparat.parameter.FocusMode;

/**
 * Capabilities of camera hardware.
 */
public interface Capabilities {

    /**
     * @return list of supported focus modes.
     */
    List<FocusMode> supportedFocusModes();

}
