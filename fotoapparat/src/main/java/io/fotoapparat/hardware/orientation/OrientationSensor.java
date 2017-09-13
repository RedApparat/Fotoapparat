package io.fotoapparat.hardware.orientation;

import android.support.annotation.NonNull;
import android.view.OrientationEventListener;

/**
 * Monitors orientation of the device.
 */
public class OrientationSensor implements RotationListener.Listener {

    private final RotationListener rotationListener;
    private final ScreenOrientationProvider screenOrientationProvider;

    private int lastKnownRotation;
    private int lastKnownOrientation = OrientationEventListener.ORIENTATION_UNKNOWN;
    private Listener listener;

    public OrientationSensor(@NonNull final RotationListener rotationListener,
                             @NonNull final ScreenOrientationProvider screenOrientationProvider) {
        this.rotationListener = rotationListener;
        this.screenOrientationProvider = screenOrientationProvider;
        this.lastKnownRotation = screenOrientationProvider.getScreenRotation();
        rotationListener.setRotationListener(this);
    }

    /**
     * Starts monitoring device's orientation.
     */
    public void start(Listener listener) {
        this.listener = listener;
        rotationListener.enable();
    }

    /**
     * Stops monitoring device's orientation.
     */
    public void stop() {
        rotationListener.disable();
        listener = null;
    }

    @Override
    public void onRotationChanged(int newOrientation) {
        if (listener != null) {
            // reduce orientation to multiple of 90
            int orientation = ((newOrientation + 45) / 90 * 90) % 360;
            int rotation = this.screenOrientationProvider.getScreenRotation();
            if (rotation != lastKnownRotation || orientation != lastKnownOrientation) {
                listener.onOrientationChanged(rotation, orientation);
                lastKnownRotation = rotation;
                lastKnownOrientation = orientation;
            }
        }
    }

    /**
     * Notified when orientation of the device is updated.
     */
    public interface Listener {

        /**
         * Called when orientation of the device is updated.
         */
        void onOrientationChanged(int degrees, int orientation);
    }
}
