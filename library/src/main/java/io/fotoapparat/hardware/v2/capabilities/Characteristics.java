package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

/**
 * Wrapper around api's {@link CameraCharacteristics}
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Characteristics {

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private final CameraManager manager;

	private CameraCharacteristics cameraCharacteristics;

	public Characteristics(CameraManager manager) {
		this.manager = manager;
	}

	/**
	 * Sets the id of currently open camera.
	 *
	 * @param cameraId the device camera id
	 * @throws CameraAccessException if the camera device has been disconnected.
	 */
	public void setCameraId(String cameraId) throws CameraAccessException {
		cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
		countDownLatch.countDown();
	}

	/**
	 * Returns synchronously the camera characteristics for a given camera id.
	 *
	 * @return the {@link CameraCharacteristics}
	 */
	public CameraCharacteristics getCameraCharacteristics() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}
		return cameraCharacteristics;
	}

	/**
	 * Informs whether or not the camera's lens has fixed focus.
	 *
	 * @return {@code true} if the camera's lens has fixed focus.
	 */
	public boolean isFixedFocusLens() {
		Float minimumFocusDistance = getCameraCharacteristics()
				.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);

		return minimumFocusDistance != null && minimumFocusDistance != 0;
	}

	/**
	 * Informs whether or not the camera has legacy hardware.
	 *
	 * @return {@code true} if the camera's lens has legacy hardware.
	 */
	@SuppressWarnings("ConstantConditions")
	public boolean isLegacyDevice() {
		int hardwareLevel = getCameraCharacteristics()
				.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);

		return hardwareLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY;
	}
}
