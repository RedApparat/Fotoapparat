package io.fotoapparat.hardware.provider;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.log.Logger;
import io.fotoapparat.util.SDKInfo;

/**
 * Selects correct version of providers for a device.
 */
public class DefaultProvider implements CameraProvider {

	private final CameraProvider v1Provider;
	private final CameraProvider v2Provider;
	private final SDKInfo sdkInfo;

	public DefaultProvider(CameraProvider v1Provider,
						   CameraProvider v2Provider) {
		this(v1Provider, v2Provider, SDKInfo.getInstance());
	}

	DefaultProvider(CameraProvider v1Provider,
					CameraProvider v2Provider,
					SDKInfo sdkInfo) {
		this.v1Provider = v1Provider;
		this.v2Provider = v2Provider;
		this.sdkInfo = sdkInfo;
	}

	@Override
	public CameraDevice get(Logger logger) {
		return sdkInfo.isBellowLollipop()
				? v1Provider.get(logger)
				: v2Provider.get(logger);
	}
}
