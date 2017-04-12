package io.fotoapparat.hardware.v2.capabilities;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;

/**
 * Creates the {@link Capabilities} of a {@link io.fotoapparat.hardware.v2.Camera2}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CapabilitiesFactory implements CapabilitiesOperator {
	private final SizeCapability sizeCapability;
	private final FocusCapability focusCapability;
	private final FlashCapability flashCapability;

	public CapabilitiesFactory(SizeCapability sizeCapability,
							   FocusCapability focusCapability,
							   FlashCapability flashCapability) {
		this.sizeCapability = sizeCapability;
		this.focusCapability = focusCapability;
		this.flashCapability = flashCapability;
	}

	@Override
	public Capabilities getCapabilities() {
		return new Capabilities(
				sizeCapability.availableJpegSizes(),
				focusCapability.availableFocusModes(),
				flashCapability.availableFlashModes()
		);
	}

}
