package io.fotoapparat.hardware.v2.parameters.converters;

import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.util.BidirectionalHashMap;

/**
 * Facilitates interactions between Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_MODE}
 * flash firing modes and {@link io.fotoapparat.Fotoapparat}'s {@link Flash}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FlashConverter {

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

    /**
     * Converts a Android native {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_MODE} to
     * a {@link Flash}.
     *
     * @param exposureMode The native Android {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_MODE}
     *                     value.
     * @return The {@link io.fotoapparat.Fotoapparat}'s camera {@link Flash} value.
     */
    public static Flash exposureModeToFlash(int exposureMode) {
        return FLASH_EXPOSURE_MAP.reversed().get(exposureMode);
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
}
