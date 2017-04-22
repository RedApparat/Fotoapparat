package io.fotoapparat.hardware.v1.capabilities;

import android.hardware.Camera;

import java.util.HashMap;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.util.BidirectionalHashMap;

/**
 * Maps between {@link Flash} and Camera v1 flash codes.
 */
@SuppressWarnings("deprecation")
public class FlashCapability {

    private static final BidirectionalHashMap<String, Flash> CODE_TO_FLASH;

    static {
        HashMap<String, Flash> map = new HashMap<>();
        map.put(Camera.Parameters.FLASH_MODE_AUTO, Flash.AUTO);
        map.put(Camera.Parameters.FLASH_MODE_RED_EYE, Flash.AUTO_RED_EYE);
        map.put(Camera.Parameters.FLASH_MODE_ON, Flash.ON);
        map.put(Camera.Parameters.FLASH_MODE_TORCH, Flash.TORCH);
        map.put(Camera.Parameters.FLASH_MODE_OFF, Flash.OFF);

        CODE_TO_FLASH = new BidirectionalHashMap<>(map);
    }

    /**
     * @param code code of flash mode as in {@link Camera.Parameters}.
     * @return {@link Flash} from given camera code.
     */
    public static Flash toFlash(String code) {
        Flash flash = CODE_TO_FLASH.forward().get(code);
        if (flash == null) {
            return Flash.OFF;
        }

        return flash;
    }

    /**
     * @param flash flash mode.
     * @return code of the flash mode as in {@link Camera.Parameters}.
     */
    public static String toCode(Flash flash) {
        return CODE_TO_FLASH.reversed().get(flash);
    }

}
