package io.fotoapparat.hardware.provider;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.v1.Camera1;

/**
 * Always provides {@link Camera1}.
 */
public class V1Provider implements CameraProvider {

	@Override
	public CameraDevice get() {
		return new Camera1();
	}

}
