package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;

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
				FocusCapability.availableFocusModes(cameraCharacteristics),
				FlashCapability.availableFlashModes(cameraCharacteristics)
		);
	}


}
