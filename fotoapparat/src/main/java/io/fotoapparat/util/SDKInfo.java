package io.fotoapparat.util;

import android.os.Build;

/**
 * Information about system's SDK.
 */
public class SDKInfo {

    private static final SDKInfo INSTANCE = new SDKInfo();

    /**
     * @return The single instance of this class.
     */
    public static SDKInfo getInstance() {
        return INSTANCE;
    }

    /**
     * @return {@code true} if the SDK version of the system is below {@link
     * Build.VERSION_CODES#LOLLIPOP} (API 21).
     */
    public boolean isBellowLollipop() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }
}
