package io.fotoapparat.hardware.v2.selection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.parameter.LensPosition;

/**
 * Finds the device's camera from the {@link android.hardware.camera2.CameraManager}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraSelector {

	private final android.hardware.camera2.CameraManager manager;

	public CameraSelector(android.hardware.camera2.CameraManager manager) {
		this.manager = manager;
	}

	/**
	 * @param lensPosition the position of the lens relatively to the device's screen
	 * @return the id of the camera as returned from the {@link android.hardware.camera2.CameraManager}
	 * based on the given lens parameter
	 */
	@NonNull
	public String findCameraId(LensPosition lensPosition) {
		try {
			return getCameraIdUnsafe(lensPosition);
		} catch (CameraAccessException e) {
			throw new CameraException(e);
		}
	}

	@NonNull
	@SuppressWarnings("ConstantConditions")
	private String getCameraIdUnsafe(LensPosition lensPosition) throws CameraAccessException {
		final String[] cameraIdList = manager.getCameraIdList();

		for (String cameraId : cameraIdList) {

			CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
			Integer lensFacingConstant = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);

			if (lensFacingConstant == getCameraCharacteristicPosition(lensPosition)) {
				return cameraId;
			}
		}
		throw new CameraException("No camera found with position: " + lensPosition);
	}

	private int getCameraCharacteristicPosition(LensPosition lensPosition) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return getCameraCharacteristicPositionM(lensPosition);
		}
		return getCameraCharacteristicPositionLollipop(lensPosition);
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	private int getCameraCharacteristicPositionM(LensPosition lensPosition) {
		switch (lensPosition) {
			case FRONT:
				return CameraCharacteristics.LENS_FACING_FRONT;
			case BACK:
				return CameraCharacteristics.LENS_FACING_BACK;
			case EXTERNAL:
				return CameraCharacteristics.LENS_FACING_EXTERNAL;
		}
		throw new IllegalStateException("Cannot return CameraCharacteristic for LensPosition: " + lensPosition);
	}

	private int getCameraCharacteristicPositionLollipop(LensPosition lensPosition) {
		switch (lensPosition) {
			case FRONT:
				return CameraCharacteristics.LENS_FACING_FRONT;
			case BACK:
				return CameraCharacteristics.LENS_FACING_BACK;
		}
		throw new IllegalStateException("Cannot return CameraCharacteristic for LensPosition: " + lensPosition);
	}
}
