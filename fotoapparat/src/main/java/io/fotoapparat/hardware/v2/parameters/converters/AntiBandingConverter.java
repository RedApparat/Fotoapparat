package io.fotoapparat.hardware.v2.parameters.converters;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import io.fotoapparat.parameter.AntiBandingMode;
import io.fotoapparat.util.BidirectionalHashMap;

/**
 * Facilitates interactions between Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_ANTIBANDING_MODE}
 * anti banding modes and {@link io.fotoapparat.Fotoapparat}'s {@link AntiBandingMode}.
 */
@SuppressLint("UseSparseArrays")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AntiBandingConverter {

    private final static BidirectionalHashMap<Integer, AntiBandingMode> AE_MODE_ANTI_BANDING_MAP;

    static {
        Map<Integer, AntiBandingMode> aeModeAntiBandingMap = new HashMap<>();
        aeModeAntiBandingMap.put(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_AUTO, AntiBandingMode.AUTO);
        aeModeAntiBandingMap.put(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_50HZ, AntiBandingMode.HZ50);
        aeModeAntiBandingMap.put(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_60HZ, AntiBandingMode.HZ60);
        aeModeAntiBandingMap.put(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_OFF, AntiBandingMode.NONE);

        AE_MODE_ANTI_BANDING_MAP = new BidirectionalHashMap<>(aeModeAntiBandingMap);
    }

    /**
     * Converts a Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_ANTIBANDING_MODE} to
     * a {@link AntiBandingMode}.
     *
     * @param aeMode The native Android {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_ANTIBANDING_MODE}
     *               value.
     * @return The {@link io.fotoapparat.Fotoapparat}'s camera {@link AntiBandingMode} value.
     */
    public static AntiBandingMode aeModeToAntiBanding(int aeMode) {
        AntiBandingMode antiBandingMode = AE_MODE_ANTI_BANDING_MAP.forward().get(aeMode);
        if (antiBandingMode == null) {
            return AntiBandingMode.NONE;
        }
        return antiBandingMode;
    }

    /**
     * Converts a {@link AntiBandingMode} to a Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_ANTIBANDING_MODE}
     *
     * @param antiBandingMode The {@link io.fotoapparat.Fotoapparat}'s camera {@link AntiBandingMode} value.
     * @return The native Android {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_ANTIBANDING_MODE}
     * value.
     */
    public static int antiBandingToAeMode(AntiBandingMode antiBandingMode) {
        Integer aeMode = AE_MODE_ANTI_BANDING_MAP.reversed().get(antiBandingMode);
        if (aeMode == null) {
            return CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_OFF;
        }

        return aeMode;
    }

}
