package io.fotoapparat.hardware.v2.capabilities;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.util.BidirectionalHashMap;

/**
 * Facilitates interactions between Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE}
 * focus modes and {@link io.fotoapparat.Fotoapparat}'s {@link FocusMode}.
 */
@SuppressLint("UseSparseArrays")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FocusCapability {

	private final static BidirectionalHashMap<Integer, FocusMode> AF_MODE_FOCUS_MAP;


	static {
		Map<Integer, FocusMode> afModeFocusMap = new HashMap<>();
		afModeFocusMap.put(CameraMetadata.CONTROL_AF_MODE_AUTO, FocusMode.AUTO);
		afModeFocusMap.put(CameraMetadata.CONTROL_AF_MODE_MACRO, FocusMode.MACRO);
		afModeFocusMap.put(CameraMetadata.CONTROL_AF_MODE_EDOF, FocusMode.EDOF);
		afModeFocusMap.put(CameraMetadata.CONTROL_AF_MODE_OFF, FocusMode.FIXED);
		afModeFocusMap.put(CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE,
				FocusMode.CONTINUOUS_FOCUS);

		AF_MODE_FOCUS_MAP = new BidirectionalHashMap<>(afModeFocusMap);
	}

	/**
	 * Converts a Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE} to
	 * a {@link FocusMode}.
	 *
	 * @param afMode The native Android {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE}
	 * @return The {@link io.fotoapparat.Fotoapparat}'s camera {@link FocusMode}
	 */
	public static FocusMode afModeToFocus(int afMode) {
		FocusMode focusMode = AF_MODE_FOCUS_MAP.forward().get(afMode);
		if (focusMode == null) {
			return FocusMode.FIXED;
		}
		return focusMode;
	}


	/**
	 * Converts a {@link FocusMode} to a Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE}
	 *
	 * @param focusMode The {@link io.fotoapparat.Fotoapparat}'s camera {@link FocusMode}
	 * @return The native Android {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE}
	 */
	public static int focusToAfMode(FocusMode focusMode) {
		Integer afMode = AF_MODE_FOCUS_MAP.reversed().get(focusMode);
		if (afMode == null) {
			return CameraMetadata.CONTROL_AF_MODE_OFF;
		}

		return afMode;
	}

	/**
	 * Returns the available Focus Modes for the given {@link CameraCharacteristics}.
	 *
	 * @param cameraCharacteristics The {@link CameraCharacteristics} from which the set will be
	 *                              generated.
	 * @return A set of available Focus Modes of a {@link android.hardware.camera2.CameraDevice}.
	 */
	@SuppressWarnings("ConstantConditions")
	static Set<FocusMode> availableFocusModes(CameraCharacteristics cameraCharacteristics) {
		int[] afModes = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);

		Set<FocusMode> focusModes = new HashSet<>();
		for (int afMode : afModes) {
			focusModes.add(FocusCapability.afModeToFocus(afMode));
		}

		return focusModes;
	}

}
