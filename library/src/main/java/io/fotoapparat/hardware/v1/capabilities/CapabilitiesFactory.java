package io.fotoapparat.hardware.v1.capabilities;

import android.hardware.Camera;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v1.Camera1;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;

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
				extractPictureSizes(parameters),
				extractPreviewSizes(parameters),
				extractFocusModes(parameters),
				extractFlashModes(parameters)
		);
	}

	private Set<Size> extractPreviewSizes(Camera.Parameters parameters) {
		return mapSizes(parameters.getSupportedPreviewSizes());
	}

	private Set<Size> extractPictureSizes(Camera.Parameters parameters) {
		return mapSizes(parameters.getSupportedPictureSizes());
	}

	private Set<Size> mapSizes(Collection<Camera.Size> sizes) {
		HashSet<Size> result = new HashSet<>();

		for (Camera.Size size : sizes) {
			result.add(new Size(
					size.width,
					size.height
			));
		}

		return result;
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
