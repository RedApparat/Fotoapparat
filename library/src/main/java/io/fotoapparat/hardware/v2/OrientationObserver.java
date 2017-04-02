package io.fotoapparat.hardware.v2;

import android.support.annotation.NonNull;

/**
 *
 */
interface OrientationObserver {

	void setOrientationListener(@NonNull OrientationListener listener);

	interface OrientationListener {
		void onOrientationChanged(int orientation);
	}
}
