package io.fotoapparat.hardware.v1.capabilities;

import android.annotation.SuppressLint;
import android.hardware.Camera;

import java.util.HashMap;
import java.util.Map;

import io.fotoapparat.parameter.AntiBandingMode;
import io.fotoapparat.util.BidirectionalHashMap;

/**
 * Maps between {@link AntiBandingMode} and Camera v1 anti banding mode codes.
 */
@SuppressLint("UseSparseArrays")
@SuppressWarnings("deprecation")
public class AntiBandingCapability {

    private final static BidirectionalHashMap<String, AntiBandingMode> CODE_TO_ANTI_BANDING_MODE;

    static {
        Map<String, AntiBandingMode> map = new HashMap<>();
        map.put(Camera.Parameters.ANTIBANDING_AUTO, AntiBandingMode.AUTO);
        map.put(Camera.Parameters.ANTIBANDING_50HZ, AntiBandingMode.HZ50);
        map.put(Camera.Parameters.ANTIBANDING_60HZ, AntiBandingMode.HZ60);
        map.put(Camera.Parameters.ANTIBANDING_OFF, AntiBandingMode.NONE);

        CODE_TO_ANTI_BANDING_MODE = new BidirectionalHashMap<>(map);
    }

    /**
     * Converts an anti banding mode code to a {@link AntiBandingMode}.
     *
     * @param code code of the anti banding mode as in {@link Camera.Parameters}.
     * @return The {@link io.fotoapparat.Fotoapparat}'s camera {@link AntiBandingMode}.
     */
    public static AntiBandingMode toAntiBandingMode(String code) {
        AntiBandingMode antiBandingMode = CODE_TO_ANTI_BANDING_MODE.forward().get(code);
        if (antiBandingMode == null) {
            return AntiBandingMode.NONE;
        }
        return antiBandingMode;
    }

    /**
     * Converts a {@link AntiBandingMode} to a antiBandingMode mode code as in {@link Camera.Parameters}.
     *
     * @param antiBandingMode The {@link io.fotoapparat.Fotoapparat}'s camera {@link AntiBandingMode}.
     * @return anti banding mode code as in {@link Camera.Parameters}.
     */
    public static String toCode(AntiBandingMode antiBandingMode) {
        return CODE_TO_ANTI_BANDING_MODE.reversed().get(antiBandingMode);
    }

}
