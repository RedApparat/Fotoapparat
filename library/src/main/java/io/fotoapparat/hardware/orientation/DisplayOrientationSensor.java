package io.fotoapparat.hardware.orientation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;

import java.util.HashMap;

/**
 * Monitors orientation of the device's display.
 */
public class DisplayOrientationSensor {

	private static final HashMap<Integer, Integer> SURFACE_ORIENTATIONS = new HashMap<>(4);
	private static final int SURFACE_ROTATION_UNKNOWN = -1;

	private static final int ROTATION_DEGREES_0 = 0;
	private static final int ROTATION_DEGREES_90 = 90;
	private static final int ROTATION_DEGREES_180 = 180;
	private static final int ROTATION_DEGREES_270 = 270;

	static {
		SURFACE_ORIENTATIONS.put(Surface.ROTATION_0, ROTATION_DEGREES_0);
		SURFACE_ORIENTATIONS.put(Surface.ROTATION_90, ROTATION_DEGREES_90);
		SURFACE_ORIENTATIONS.put(Surface.ROTATION_180, ROTATION_DEGREES_180);
		SURFACE_ORIENTATIONS.put(Surface.ROTATION_270, ROTATION_DEGREES_270);
	}

	private final OrientationEventListener orientationEventListener;

	private int lastDisplayRotation = SURFACE_ROTATION_UNKNOWN;
	private Listener listener;

	public DisplayOrientationSensor(@NonNull Context context) {
		final Display display = getDisplay(context);

		orientationEventListener = new OrientationEventListener(context) {

			@Override
			public void onOrientationChanged(int orientation) {
				if (listener != null && canDetectOrientation()) {
					dispatchDisplayOrientation(display.getRotation());
				}
			}
		};
	}

	private static Display getDisplay(@NonNull Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay();
	}

	private void dispatchDisplayOrientation(int displayRotation) {
		if (displayRotation == lastDisplayRotation) {
			return;
		}

		lastDisplayRotation = displayRotation;

		int surfaceOrientationDegrees = SURFACE_ORIENTATIONS.get(displayRotation);
		listener.onOrientationChanged(surfaceOrientationDegrees);
	}

	/**
	 * Starts monitoring device's orientation.
	 */
	public void start(Listener listener) {
		this.listener = listener;
		orientationEventListener.enable();
	}

	/**
	 * Stops monitoring device's orientation.
	 */
	public void stop() {
		orientationEventListener.disable();
		listener = null;
	}

	/**
	 * Notified when orientation of the device is updated.
	 */
	public interface Listener {

		/**
		 * Called when orientation of the device is updated.
		 *
		 * @param degrees orientation of the device's display (relatively to user's eyes).
		 *                One of: 0, 90, 270, and 360
		 */
		void onOrientationChanged(int degrees);
	}
}
