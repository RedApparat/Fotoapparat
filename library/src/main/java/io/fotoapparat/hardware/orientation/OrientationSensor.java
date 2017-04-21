package io.fotoapparat.hardware.orientation;

import android.support.annotation.NonNull;

/**
 * Monitors orientation of the device.
 */
public class OrientationSensor implements RotationListener.Listener {

    private final RotationListener rotationListener;
    private final ScreenOrientationProvider screenOrientationProvider;

    private int lastKnownRotation;
    private Listener listener;

    public OrientationSensor(@NonNull final RotationListener rotationListener,
                             @NonNull final ScreenOrientationProvider screenOrientationProvider) {
        this.rotationListener = rotationListener;
        this.screenOrientationProvider = screenOrientationProvider;

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
    public void onRotationChanged() {
        if (listener != null) {
            int rotation = screenOrientationProvider.getScreenRotation();
            if (rotation != lastKnownRotation) {
                listener.onOrientationChanged(rotation);
                lastKnownRotation = rotation;
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
        void onOrientationChanged(int degrees);
    }
}
