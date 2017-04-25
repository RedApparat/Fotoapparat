package io.fotoapparat.hardware.v2.orientation;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import io.fotoapparat.hardware.operators.OrientationOperator;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.connection.CameraConnection;

/**
 * Object is which is aware of orientation related values.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class OrientationManager implements OrientationOperator {

    private final List<Listener> listeners = new ArrayList<>();
    private final CameraConnection cameraConnection;
    private int orientation;

    public OrientationManager(CameraConnection cameraConnection) {
        this.cameraConnection = cameraConnection;
    }

    /**
     * Notifies that the display orientation has changed.
     *
     * @param orientation the display orientation in degrees. One of: 0, 90, 180 and 270
     */
    @Override
    public void setDisplayOrientation(int orientation) {
        this.orientation = orientation;
        for (Listener listener : listeners) {
            listener.onDisplayOrientationChanged(orientation);
        }
    }

    /**
     * Sensor orientation is 90 for most devices, or 270 for some devices (eg. Nexus 5X)
     * We have to take that into account and rotate JPEG properly.
     *
     * @return The clockwise rotation angle in degrees, relative to the orientation to the camera,
     * that the JPEG picture needs to be rotated by, to be viewed upright.
     */
    @SuppressWarnings("ConstantConditions")
    public Integer getSensorOrientation() {
        return (cameraConnection.getCharacteristics().getSensorOrientation() - orientation + 360) % 360;
    }

    /**
     * Adds a listener to be notified when the orientation has changed.
     *
     * @param listener the listener to be notified
     */
    public synchronized void addListener(Listener listener) {
        if (listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

    /**
     * Notifies that the display orientation has changed.
     **/
    public interface Listener {

        /**
         * Called when the display orientation has changed.
         *
         * @param orientation the display orientation in degrees. One of: 0, 90, 180 and 270
         */
        void onDisplayOrientationChanged(int orientation);
    }
}
