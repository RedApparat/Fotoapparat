package io.fotoapparat.hardware.v1.capabilities;

import android.annotation.SuppressLint;
import android.hardware.Camera;

import java.util.HashMap;
import java.util.Map;

import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.util.BidirectionalHashMap;

/**
 * Maps between {@link FocusMode} and Camera v1 focus mode codes.
 */
@SuppressLint("UseSparseArrays")
@SuppressWarnings("deprecation")
public class FocusCapability {

    private final static BidirectionalHashMap<String, FocusMode> CODE_TO_FOCUS_MODE;

    static {
        Map<String, FocusMode> map = new HashMap<>();
        map.put(Camera.Parameters.FOCUS_MODE_AUTO, FocusMode.AUTO);
        map.put(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE, FocusMode.CONTINUOUS_FOCUS);
        map.put(Camera.Parameters.FOCUS_MODE_MACRO, FocusMode.MACRO);
        map.put(Camera.Parameters.FOCUS_MODE_EDOF, FocusMode.EDOF);
        map.put(Camera.Parameters.FOCUS_MODE_INFINITY, FocusMode.INFINITY);
        map.put(Camera.Parameters.FOCUS_MODE_FIXED, FocusMode.FIXED);

        CODE_TO_FOCUS_MODE = new BidirectionalHashMap<>(map);
    }

    /**
     * Converts a focus mode code to a {@link FocusMode}.
     *
     * @param code code of the focus mode as in {@link Camera.Parameters}.
     * @return The {@link io.fotoapparat.Fotoapparat}'s camera {@link FocusMode}.
     */
    public static FocusMode toFocusMode(String code) {
        FocusMode focusMode = CODE_TO_FOCUS_MODE.forward().get(code);
        if (focusMode == null) {
            return FocusMode.FIXED;
        }
        return focusMode;
    }

    /**
     * Converts a {@link FocusMode} to a focus mdoe code as in {@link Camera.Parameters}.
     *
     * @param focusMode The {@link io.fotoapparat.Fotoapparat}'s camera {@link FocusMode}.
     * @return focus mode code as in {@link Camera.Parameters}.
     */
    public static String toCode(FocusMode focusMode) {
        return CODE_TO_FOCUS_MODE.reversed().get(focusMode);
    }

}
