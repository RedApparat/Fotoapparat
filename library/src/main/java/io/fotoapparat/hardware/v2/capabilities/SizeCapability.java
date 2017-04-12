package io.fotoapparat.hardware.v2.capabilities;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.fotoapparat.parameter.Size;
import io.fotoapparat.util.CompareSizesByArea;

/**
 * Provides infromation about the possible sizes the camera can take pictures of.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SizeCapability {

	private final Characteristics characteristics;

	public SizeCapability(Characteristics characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * @return the largest size the camera can take a picture of
	 */
	@SuppressWarnings("ConstantConditions")
	public Size getLargestSize() {
		return Collections.max(
				availableJpegSizes(),
				new CompareSizesByArea()
		);
	}

	@SuppressWarnings("ConstantConditions")
	public Set<Size> availableJpegSizes() {
		StreamConfigurationMap configurationMap = characteristics
				.getCameraCharacteristics()
				.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

		android.util.Size[] availableSizes = configurationMap.getOutputSizes(ImageFormat.JPEG);

		return convertSizesType(availableSizes);
	}

	private HashSet<Size> convertSizesType(android.util.Size[] availableSizes) {
		HashSet<Size> sizesSet = new HashSet<>(availableSizes.length);

		for (android.util.Size size : Arrays.asList(availableSizes)) {
			sizesSet.add(new Size(size.getWidth(), size.getHeight()));
		}

		return sizesSet;
	}

}
