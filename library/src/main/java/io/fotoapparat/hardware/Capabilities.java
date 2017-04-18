package io.fotoapparat.hardware;

import java.util.Collections;
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
	 * @return Empty {@link Capabilities}.
	 */
	public static Capabilities empty() {
		return new Capabilities(
				Collections.<Size>emptySet(),
				Collections.<FocusMode>emptySet(),
				Collections.<Flash>emptySet()
		);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Capabilities that = (Capabilities) o;

		return sizes != null ? sizes.equals(that.sizes) : that.sizes == null
				&& (focusModes != null ? focusModes.equals(that.focusModes) : that.focusModes == null
				&& (flashModes != null ? flashModes.equals(that.flashModes) : that.flashModes == null));

	}

	@Override
	public int hashCode() {
		int result = sizes != null ? sizes.hashCode() : 0;
		result = 31 * result + (focusModes != null ? focusModes.hashCode() : 0);
		result = 31 * result + (flashModes != null ? flashModes.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Capabilities{" +
				"sizes=" + sizes +
				", focusModes=" + focusModes +
				", flashModes=" + flashModes +
				'}';
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
