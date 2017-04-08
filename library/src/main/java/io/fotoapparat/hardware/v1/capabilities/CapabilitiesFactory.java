package io.fotoapparat.hardware.v1.capabilities;

import android.hardware.Camera;

import java.util.HashSet;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v1.Camera1;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;

/**
 * {@link Capabilities} of {@link Camera1}.
 */
@SuppressWarnings("deprecation")
public class CapabilitiesFactory {

	/**
	 * @return {@link Capabilities} from given camera parameters.
	 */
	public Capabilities fromParameters(Camera.Parameters parameters) {
		return new Capabilities(
				extractFocusModes(parameters),
				extractFlashModes(parameters)
		);
	}

	private Set<Flash> extractFlashModes(Camera.Parameters parameters) {
		HashSet<Flash> result = new HashSet<>();

		for (String flashMode : parameters.getSupportedFlashModes()) {
			result.add(
					FlashCapability.toFlash(flashMode)
			);
		}

		return result;
	}

	private Set<FocusMode> extractFocusModes(Camera.Parameters parameters) {
		HashSet<FocusMode> result = new HashSet<>();

		for (String focusMode : parameters.getSupportedFocusModes()) {
			result.add(
					FocusCapability.toFocusMode(focusMode)
			);
		}

		return result;
	}

}
