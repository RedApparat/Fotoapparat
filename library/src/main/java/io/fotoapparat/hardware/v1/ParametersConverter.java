package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

import io.fotoapparat.hardware.v1.capabilities.FocusCapability;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;

/**
 * Converts {@link Parameters} to {@link Camera.Parameters}.
 */
@SuppressWarnings("deprecation")
public class ParametersConverter {

	/**
	 * Converts {@link Parameters} to {@link Camera.Parameters}.
	 *
	 * @param parameters parameters which should be converted.
	 * @param output     output value. It is required because of C-style API in Camera v1.
	 * @return same object which was passed as {@code output}, but filled with new parameters.
	 */
	public Camera.Parameters convert(Parameters parameters, Camera.Parameters output) {
		for (Parameters.Type storedType : parameters.storedTypes()) {
			applyParameter(
					storedType,
					parameters,
					output
			);
		}

		return output;
	}

	private void applyParameter(Parameters.Type type,
								Parameters input,
								Camera.Parameters output) {
		switch (type) {
			case FOCUS_MODE:
				applyFocusMode(
						(FocusMode) input.getValue(type),
						output
				);
				break;
		}
	}

	private void applyFocusMode(FocusMode focusMode,
								Camera.Parameters output) {
		output.setFocusMode(
				FocusCapability.toCode(focusMode)
		);
	}

}
