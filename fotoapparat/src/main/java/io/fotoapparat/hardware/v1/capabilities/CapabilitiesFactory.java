package io.fotoapparat.hardware.v1.capabilities;

import android.hardware.Camera;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v1.Camera1;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.DiscreetRange;

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
				extractFlashModes(parameters),
				extractSupportsSensorSensitivityValue(parameters)
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

		for (String flashMode : supportedFlashModes(parameters)) {
			result.add(
					FlashCapability.toFlash(flashMode)
			);
		}

		return result;
	}

	@NonNull
	private List<String> supportedFlashModes(Camera.Parameters parameters) {
		List<String> supportedFlashModes = parameters.getSupportedFlashModes();
		return supportedFlashModes != null
				? supportedFlashModes
				: Collections.singletonList(Camera.Parameters.FLASH_MODE_OFF);
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

	private DiscreetRange extractSupportsSensorSensitivityValue(Camera.Parameters parameters) {
		final Set<Integer> isoValuesSet = new HashSet<>();

		// Based on https://stackoverflow.com/a/23567103/791323
		String flat = parameters.flatten();
		String[] isoValues = null;
		String values_keyword=null;
		String iso_keyword=null;
		if(flat.contains("iso-values")) {
			// most used keywords
			values_keyword="iso-values";
			iso_keyword="iso";
		} else if(flat.contains("iso-mode-values")) {
			// google galaxy nexus keywords
			values_keyword="iso-mode-values";
			iso_keyword="iso";
		} else if(flat.contains("iso-speed-values")) {
			// micromax a101 keywords
			values_keyword="iso-speed-values";
			iso_keyword="iso-speed";
		} else if(flat.contains("nv-picture-iso-values")) {
			// LG dual p990 keywords
			values_keyword="nv-picture-iso-values";
			iso_keyword="nv-picture-iso";
		}
		// add other eventual keywords here...
		if(iso_keyword!=null) {
			// flatten contains the iso key!!
			String iso = flat.substring(flat.indexOf(values_keyword));
			iso = iso.substring(iso.indexOf("=")+1);

			if(iso.contains(";")) iso = iso.substring(0, iso.indexOf(";"));

			isoValues = iso.split(",");
			for(String value: isoValues) {
				isoValuesSet.add(Integer.valueOf(value));
			}

		}

		return new DiscreetRange<>(isoValuesSet);
	}

}
