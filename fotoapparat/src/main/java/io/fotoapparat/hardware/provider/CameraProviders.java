package io.fotoapparat.hardware.provider;

import android.content.Context;

/**
 * Static factory for {@link CameraProvider}
 */
public class CameraProviders {

	private CameraProviders() {
	}

	/**
	 * @return provider which uses Camera v2 on devices newer than Lollipop and falls back to Camera
	 * v1 on older devices.
	 */
	public static CameraProvider defaultProvider(Context context) {
		return new DefaultProvider(
				v1(),
				v2(context)
		);
	}

	/**
	 * @return provider for Camera v1.
	 */
	public static CameraProvider v1() {
		return new V1Provider();
	}

	/**
	 * @return provider for Camera v2.
	 */
	public static CameraProvider v2(Context context) {
		return new V2Provider(context);
	}

}
