package io.fotoapparat.hardware.v2.capabilities;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;

import static io.fotoapparat.hardware.v2.capabilities.FlashConverter.exposureModeToFlash;

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
		return new Capabilities(
				availableJpegSizes(),
				availableFocusModes(),
				availableFlashModes()
		);
	}

	@SuppressWarnings("ConstantConditions")
	private Set<Size> availableJpegSizes() {
		android.util.Size[] availableSizes = characteristics.getJpegOutputSizes();
		HashSet<Size> sizesSet = new HashSet<>(availableSizes.length);

		for (android.util.Size size : Arrays.asList(availableSizes)) {
			sizesSet.add(new Size(size.getWidth(), size.getHeight()));
		}

		return sizesSet;
	}

	@SuppressWarnings("ConstantConditions")
	private Set<FocusMode> availableFocusModes() {
		Set<FocusMode> focusModes = new HashSet<>();
		for (int afMode : characteristics.autoFocusModes()) {
			focusModes.add(FocusConverter.afModeToFocus(afMode));
		}

		return focusModes;
	}

	@SuppressWarnings("ConstantConditions")
	private Set<Flash> availableFlashModes() {
		if (characteristics.isFlashAvailable()) {
			return availableFlashUnitModes();
		}
		return Collections.singleton(Flash.OFF);

	}

	@SuppressWarnings("ConstantConditions")
	private Set<Flash> availableFlashUnitModes() {
		Set<Flash> flashes = new HashSet<>();
		flashes.add(Flash.OFF);
		flashes.add(Flash.TORCH);

		int[] autoExposureModes = characteristics.autoExposureModes();

		for (int autoExposureMode : autoExposureModes) {
			Flash flash = exposureModeToFlash(autoExposureMode);
			if (flash != null) {
				flashes.add(flash);
			}
		}

		return flashes;
	}

}
