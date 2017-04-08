package io.fotoapparat.hardware.v1.capabilities;

import android.hardware.Camera;

import java.util.HashSet;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v1.Camera1;
import io.fotoapparat.parameter.FocusMode;

/**
 * {@link Capabilities} of {@link Camera1}.
 */
@SuppressWarnings("deprecation")
public class CapabilitiesFactory {

	public Capabilities fromParameters(Camera.Parameters parameters) {
		return new Capabilities(
				extractFocusModes(parameters),
				null
		);
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
