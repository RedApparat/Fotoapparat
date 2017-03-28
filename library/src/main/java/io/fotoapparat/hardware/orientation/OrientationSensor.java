package io.fotoapparat.hardware.orientation;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.view.OrientationEventListener;

/**
 * Monitors orientation of the device.
 */
public class OrientationSensor {

	private final OrientationEventListener orientationEventListener;

	public OrientationSensor(@NonNull Context context,
							 @NonNull final Listener listener) {

		orientationEventListener = new OrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL) {

			@Override
			public void onOrientationChanged(int orientation) {
				if (canDetectOrientation()) {
					listener.onOrientationChanged(orientation);
				}
			}

		};
	}

	/**
	 * Starts monitoring device's orientation.
	 */
	public void start() {
		orientationEventListener.enable();
	}

	/**
	 * Stops monitoring device's orientation.
	 */
	public void stop() {
		orientationEventListener.disable();
	}

	/**
	 * Notified when orientation of the device is updated.
	 */
	public interface Listener {

		/**
		 * Called when orientation of the device is updated.
		 *
		 * @param degrees orientation of the device (relatively to user's eyes) in degrees.
		 */
		void onOrientationChanged(int degrees);

	}

}
