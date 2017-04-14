package io.fotoapparat.parameter.provider;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Provides initial {@link Parameters} for {@link CameraDevice}.
 */
public class InitialParametersProvider {

	private final CapabilitiesOperator capabilitiesOperator;
	private final SelectorFunction<Size> sizeSelector;
	private final SelectorFunction<FocusMode> focusModeSelector;
	private final SelectorFunction<Flash> flashSelector;

	public InitialParametersProvider(CapabilitiesOperator capabilitiesOperator,
									 SelectorFunction<Size> sizeSelector,
									 SelectorFunction<FocusMode> focusModeSelector,
									 SelectorFunction<Flash> flashSelector) {
		this.capabilitiesOperator = capabilitiesOperator;
		this.sizeSelector = sizeSelector;
		this.focusModeSelector = focusModeSelector;
		this.flashSelector = flashSelector;
	}

	/**
	 * @return {@link Parameters} which will be used by {@link CameraDevice} on start-up.
	 */
	public Parameters initialParameters() {
		Capabilities capabilities = capabilitiesOperator.getCapabilities();

		Parameters parameters = new Parameters();

		putSize(capabilities, parameters);
		putFocusMode(capabilities, parameters);
		putFlash(capabilities, parameters);

		return parameters;
	}

	private void putSize(Capabilities capabilities, Parameters parameters) {
		parameters.putValue(
				Parameters.Type.PICTURE_SIZE,
				sizeSelector.select(
						capabilities.supportedSizes()
				)
		);
	}

	private void putFocusMode(Capabilities capabilities, Parameters parameters) {
		parameters.putValue(
				Parameters.Type.FOCUS_MODE,
				focusModeSelector.select(
						capabilities.supportedFocusModes()
				)
		);
	}

	private void putFlash(Capabilities capabilities, Parameters parameters) {
		parameters.putValue(
				Parameters.Type.FLASH,
				flashSelector.select(
						capabilities.supportedFlashModes()
				)
		);
	}

}
