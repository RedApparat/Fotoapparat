package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.parameter.FocusMode;

/**
 * Creates the {@link Capabilities} of a {@link io.fotoapparat.hardware.v2.Camera2}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CapabilitiesFactory implements CapabilitiesOperator {
	private final Characteristics characteristics;

	public CapabilitiesFactory(Characteristics characteristics) {
		this.characteristics = characteristics;
	}

	@Override
	public Capabilities getCapabilities() {
		CameraCharacteristics cameraCharacteristics = characteristics.getCameraCharacteristics();

		return new Capabilities(
				getFocusModes(cameraCharacteristics)
		);
	}

	@SuppressWarnings("ConstantConditions")
	private Set<FocusMode> getFocusModes(CameraCharacteristics cameraCharacteristics) {
		int[] afModes = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);

		Set<FocusMode> focusModes = new HashSet<>();
		for (int afMode : afModes) {
			focusModes.add(mapFocus(afMode));
		}

		return focusModes;
	}

	private FocusMode mapFocus(int afMode) {
		switch (afMode) {
			case CameraMetadata.CONTROL_AF_MODE_AUTO:
				return FocusMode.AUTO;
			case CameraMetadata.CONTROL_AF_MODE_MACRO:
				return FocusMode.MACRO;
			case CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE:
				return FocusMode.CONTINUOUS_FOCUS;
			case CameraMetadata.CONTROL_AF_MODE_EDOF:
				return FocusMode.EDOF;
			case CameraMetadata.CONTROL_AF_MODE_OFF:
			default:
				return FocusMode.FIXED;
		}
	}
}
