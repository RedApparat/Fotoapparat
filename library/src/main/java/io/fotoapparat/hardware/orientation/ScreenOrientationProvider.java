package io.fotoapparat.hardware.orientation;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Surface;

/**
 * Provides orientation of the screen.
 */
public class ScreenOrientationProvider {

	private final Context context;

	public ScreenOrientationProvider(@NonNull Context context) {
		this.context = context;
	}

	/**
	 * @return rotation of the screen in degrees.
	 */
	public int getScreenRotation() {
		int rotation = ((Activity) context)
				.getWindowManager()
				.getDefaultDisplay()
				.getRotation();

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
