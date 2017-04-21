package io.fotoapparat.hardware.orientation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.OrientationEventListener;

/**
 * Wrapper around {@link OrientationEventListener} to notify when the device's rotation has changed.
 */
public class RotationListener extends OrientationEventListener {

    private Listener listener;

    public RotationListener(Context context) {
        super(context);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (listener != null && canDetectOrientation()) {
            listener.onRotationChanged();
        }
    }

    /**
     * Sets a listener to this class to notify future rotation events.
     *
     * @param listener The new listener
     */
    void setRotationListener(@NonNull Listener listener) {
        this.listener = listener;
    }

    /**
     * Notified when the rotation of the device is updated.
     */
    interface Listener {

        /**
         * Called when the rotation of the device has changed.
         */
        void onRotationChanged();
    }
}
