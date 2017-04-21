package io.fotoapparat.hardware.v2.parameters.converters;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.util.BidirectionalHashMap;

/**
 * Facilitates interactions between Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE}
 * focus modes and {@link io.fotoapparat.Fotoapparat}'s {@link FocusMode}.
 */
@SuppressLint("UseSparseArrays")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FocusConverter {

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
     *               value.
     * @return The {@link io.fotoapparat.Fotoapparat}'s camera {@link FocusMode} value.
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
     * @param focusMode The {@link io.fotoapparat.Fotoapparat}'s camera {@link FocusMode} value.
     * @return The native Android {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE}
     * value.
     */
    public static int focusToAfMode(FocusMode focusMode) {
        Integer afMode = AF_MODE_FOCUS_MAP.reversed().get(focusMode);
        if (afMode == null) {
            return CameraMetadata.CONTROL_AF_MODE_OFF;
        }

        return afMode;
    }

}
