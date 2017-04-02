package io.fotoapparat.hardware.v2.orientation;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.v2.capabilities.CameraCapabilities;

/**
 * Object is which is aware of orientation related values.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class OrientationManager {

	private final CameraCapabilities cameraCapabilities;
	private int orientation;
	private Listener listener;

	public OrientationManager(CameraCapabilities cameraCapabilities) {
		this.cameraCapabilities = cameraCapabilities;
	}

	/**
	 * Notifies that the display orientation has changed.
	 *
	 * @param orientation the display orientation in degrees. One of: 0, 90, 180 and 270
	 */
	public void onDisplayOrientationChanged(int orientation) {
		this.orientation = orientation;
		if (listener != null) {
			listener.onDisplayOrientationChanged();
		}
	}

	/**
	 * Returns the screen orientation in degrees.
	 *
	 * @return the display orientation in degrees. One of: 0, 90, 180 and 270
	 */
	public int getScreenOrientation() {
		return orientation;
	}

	/**
	 * Sensor orientation is 90 for most devices, or 270 for some devices (eg. Nexus 5X)
	 * We have to take that into account and rotate JPEG properly.
	 * For devices with orientation of 90, we simply return our mapping from ORIENTATIONS.
	 * For devices with orientation of 270, we need to rotate the JPEG 180 degrees.
	 *
	 * @return The clockwise rotation angle in degrees, relative to the orientation to the camera,
	 * that the JPEG picture needs to be rotated by, to be viewed upright.
	 */

	@SuppressWarnings("ConstantConditions")
	public Integer getSensorOrientation() {
		CameraCharacteristics cameraCharacteristics = cameraCapabilities.getCameraCharacteristics();
		Integer sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);

		return (orientation + sensorOrientation + 270) % 360;
	}

	/**
	 * Sets a listener to be notified when the orientation has changed.
	 *
	 * @param listener the listener to be notified
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/**
	 * Notifies that the display orientation has changed.
	 **/
	interface Listener {

		/**
		 * Called when the display orientation has changed.
		 */
		void onDisplayOrientationChanged();
	}
}
