package io.fotoapparat.hardware.v2.capabilities;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.util.BidirectionalHashMap;

/**
 * Facilitates interactions between Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE}
 * focus modes and {@link io.fotoapparat.Fotoapparat}'s {@link FocusMode}.
 */
@SuppressLint("UseSparseArrays")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FlashCapability {

	private final static Map<Flash, Integer> FLASH_FLASH_MODE_MAP = new HashMap<>();
	private static BidirectionalHashMap<Flash, Integer> FLASH_EXPOSURE_MAP;

	static {
		Map<Flash, Integer> flashExposureModeMap = new HashMap<>();
		flashExposureModeMap.put(Flash.ON, CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH);
		flashExposureModeMap.put(Flash.AUTO, CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH);
		flashExposureModeMap.put(Flash.AUTO_RED_EYE,
				CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE);
		flashExposureModeMap.put(Flash.TORCH, CameraMetadata.CONTROL_AE_MODE_ON);
		flashExposureModeMap.put(Flash.OFF, CameraMetadata.CONTROL_AE_MODE_ON);

		FLASH_EXPOSURE_MAP = new BidirectionalHashMap<>(flashExposureModeMap);

		FLASH_FLASH_MODE_MAP.put(Flash.ON, null);
		FLASH_FLASH_MODE_MAP.put(Flash.AUTO, null);
		FLASH_FLASH_MODE_MAP.put(Flash.AUTO_RED_EYE, null);
		FLASH_FLASH_MODE_MAP.put(Flash.TORCH, CameraMetadata.FLASH_MODE_TORCH);
		FLASH_FLASH_MODE_MAP.put(Flash.OFF, CameraMetadata.FLASH_MODE_OFF);
	}

	private final Characteristics characteristics;

	public FlashCapability(Characteristics characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * Returns the available Flash firing modes for the given {@link CameraCharacteristics}.
	 *
	 * @return A set of available Flash firing modes of a {@link android.hardware.camera2.CameraDevice}.
	 */
	@SuppressWarnings("ConstantConditions")
	public Set<Flash> availableFlashModes() {
		CameraCharacteristics cameraCharacteristics = characteristics.getCameraCharacteristics();
		boolean flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);

		if (flashAvailable) {
			return availableFlashUnitModes(cameraCharacteristics);
		}
		return Collections.singleton(Flash.OFF);

	}

	@SuppressWarnings("ConstantConditions")
	private static Set<Flash> availableFlashUnitModes(CameraCharacteristics cameraCharacteristics) {
		Set<Flash> flashes = new HashSet<>();
		flashes.add(Flash.OFF);
		flashes.add(Flash.TORCH);

		int[] autoExposureModes = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);

		Map<Integer, Flash> forward = FLASH_EXPOSURE_MAP.reversed();

		for (int autoExposureMode : autoExposureModes) {
			Flash flash = forward.get(autoExposureMode);
			if (flash != null) {
				flashes.add(flash);
			}
		}

		return flashes;
	}

	/**
	 * Converts a {@link Flash} to a Android native {@link android.hardware.camera2.CaptureRequest#FLASH_MODE}
	 *
	 * @param flash The {@link io.fotoapparat.Fotoapparat}'s camera {@link Flash} value.
	 * @return The native Android {@link android.hardware.camera2.CaptureRequest#FLASH_MODE} value.
	 */
	public static Integer flashToFiringMode(Flash flash) {
		return FLASH_FLASH_MODE_MAP.get(flash);
	}

	/**
	 * Converts a {@link Flash} to a Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_MODE}
	 *
	 * @param flash The {@link io.fotoapparat.Fotoapparat}'s camera {@link Flash} value.
	 * @return The native Android {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_MODE}
	 * value.
	 */
	public static int flashToAutoExposureMode(Flash flash) {
		return FLASH_EXPOSURE_MAP.forward().get(flash);
	}

}
