package io.fotoapparat.hardware.orientation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Provides orientation of the screen.
 */
public class ScreenOrientationProvider {

    private final Display display;

    public ScreenOrientationProvider(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    /**
     * @return rotation of the screen in degrees.
     */
    public int getScreenRotation() {
        int rotation = display.getRotation();

        switch (rotation) {
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            case Surface.ROTATION_0:
            default:
                return 0;
        }
    }
}
