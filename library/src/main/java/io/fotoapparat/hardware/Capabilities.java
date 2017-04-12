package io.fotoapparat.hardware;

import java.util.Set;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;

/**
 * Capabilities of camera hardware.
 */
public class Capabilities {

	private final Set<Size> sizes;
	private final Set<FocusMode> focusModes;
	private final Set<Flash> flashModes;

	public Capabilities(Set<Size> sizes,
						Set<FocusMode> focusModes,
						Set<Flash> flashModes) {
		this.sizes = sizes;
		this.focusModes = focusModes;
		this.flashModes = flashModes;
	}

	/**
	 * @return list of supported sizes.
	 */
	public Set<Size> supportedSizes() {
		return sizes;
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
