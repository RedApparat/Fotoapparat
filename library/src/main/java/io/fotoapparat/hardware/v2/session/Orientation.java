package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import static android.view.OrientationEventListener.ORIENTATION_UNKNOWN;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Orientation {

	private final CameraCharacteristics characteristics;

	Orientation(CameraCharacteristics characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * @param deviceOrientation The rotation of the device relative to the native device
	 *                          orientation.
	 * @return The clockwise rotation angle in degrees, relative to the orientation to the camera,
	 * that the JPEG picture needs to be rotated by, to be viewed upright.
	 */
	@SuppressWarnings("ConstantConditions")
	int getJpegOrientation(int deviceOrientation) {
		if (deviceOrientation == ORIENTATION_UNKNOWN) {
			return 0;
		}

		int sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
		int calculateDeviceOrientation = calculateDeviceOrientation(deviceOrientation);

		return (sensorOrientation + calculateDeviceOrientation + 360) % 360;
	}

	@SuppressWarnings("ConstantConditions")
	private int calculateDeviceOrientation(int deviceOrientation) {
		// Round device orientation to a multiple of 90
		deviceOrientation = (deviceOrientation + 45) / 90 * 90;

		boolean shouldReverseOrientation = characteristics.get(CameraCharacteristics.LENS_FACING) == CameraMetadata.LENS_FACING_FRONT;

		if (shouldReverseOrientation) {
			deviceOrientation *= -1;
		}
		return deviceOrientation;
	}
}
