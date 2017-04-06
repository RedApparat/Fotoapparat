package io.fotoapparat.hardware;

import java.util.Set;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;

/**
 * Capabilities of camera hardware.
 */
public class Capabilities {

	private final Set<FocusMode> focusModes;
	private final Set<Flash> flashModes;

	public Capabilities(Set<FocusMode> focusModes, Set<Flash> flashModes) {
		this.focusModes = focusModes;
		this.flashModes = flashModes;
	}

	/**
	 * @return list of supported focus modes.
	 */
	public Set<FocusMode> supportedFocusModes() {
		return focusModes;
	}

	/**
	 * @return list of supported flash firing modes.
	 */
	public Set<Flash> supportedFlashModes() {
		return flashModes;
	}
}
