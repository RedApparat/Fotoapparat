package io.fotoapparat.hardware.v2.orientation;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.operators.OrientationOperator;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;

/**
 * Object is which is aware of orientation related values.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class OrientationManager implements OrientationOperator {

	private final Characteristics characteristics;
	private int orientation;
	private Listener listener;

	public OrientationManager(Characteristics characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * Notifies that the display orientation has changed.
	 *
	 * @param orientation the display orientation in degrees. One of: 0, 90, 180 and 270
	 */
	@Override
	public void setDisplayOrientation(int orientation) {
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
	 *
	 * @return The clockwise rotation angle in degrees, relative to the orientation to the camera,
	 * that the JPEG picture needs to be rotated by, to be viewed upright.
	 */
	@SuppressWarnings("ConstantConditions")
	public Integer getSensorOrientation() {
		android.hardware.camera2.CameraCharacteristics cameraCharacteristics = this.characteristics.getCameraCharacteristics();
		Integer sensorOrientation = cameraCharacteristics.get(android.hardware.camera2.CameraCharacteristics.SENSOR_ORIENTATION);

		return (sensorOrientation - orientation + 360) % 360;
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
	public interface Listener {

		/**
		 * Called when the display orientation has changed.
		 */
		void onDisplayOrientationChanged();
	}
}
