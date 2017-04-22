package io.fotoapparat.hardware.v2.parameters.converters;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.util.BidirectionalHashMap;

import static io.fotoapparat.parameter.LensPosition.BACK;
import static io.fotoapparat.parameter.LensPosition.EXTERNAL;
import static io.fotoapparat.parameter.LensPosition.FRONT;

/**
 * Facilitates interactions between Android native {@link CameraCharacteristics#LENS_FACING}
 * focus modes and {@link io.fotoapparat.Fotoapparat}'s {@link LensPosition}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LensPositionConverter {

    private final static BidirectionalHashMap<Integer, LensPosition> LENS_FACING_MAP;

    static {
        Map<Integer, LensPosition> afModeFocusMap = new HashMap<>(3);
        afModeFocusMap.put(CameraCharacteristics.LENS_FACING_FRONT, FRONT);
        afModeFocusMap.put(CameraCharacteristics.LENS_FACING_BACK, BACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            afModeFocusMap.put(CameraCharacteristics.LENS_FACING_EXTERNAL, EXTERNAL);
        }

        LENS_FACING_MAP = new BidirectionalHashMap<>(afModeFocusMap);
    }

    /**
     * Converts a Android native {@link CameraCharacteristics#LENS_FACING} to {@link LensPosition}.
     *
     * @param lensPositionConstant The native Android {@link CameraCharacteristics#LENS_FACING}
     *                             value.
     * @return The {@link io.fotoapparat.Fotoapparat}'s camera {@link LensPosition} value.
     */
    public static LensPosition toLensPosition(Integer lensPositionConstant) {
        return LENS_FACING_MAP.forward().get(lensPositionConstant);
    }

    /**
     * Converts a {@link LensPosition} to Android native {@link CameraCharacteristics#LENS_FACING}.
     *
     * @param lensPosition The {@link io.fotoapparat.Fotoapparat}'s camera {@link LensPosition}
     *                     value.
     * @return The native Android {@link CameraCharacteristics#LENS_FACING} value.
     */

    public static Integer toLensFacingConstant(LensPosition lensPosition) {
        return LENS_FACING_MAP.reversed().get(lensPosition);
    }
}
