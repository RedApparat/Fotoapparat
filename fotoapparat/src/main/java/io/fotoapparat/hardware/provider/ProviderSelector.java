package io.fotoapparat.hardware.provider;

import android.content.Context;

import io.fotoapparat.util.SDKInfo;

/**
 * Selects correct version of providers for a device.
 */
public class ProviderSelector {

    private Context context;
    private SDKInfo sdkInfo;

    public ProviderSelector(Context context) {
        this(context, SDKInfo.getInstance());
    }

    ProviderSelector(Context context, SDKInfo sdkInfo) {
        this.context = context;
        this.sdkInfo = sdkInfo;
    }

    /**
     * @return The selected {@link AvailableLensPositionsProvider} supported by this device.
     */
    public AvailableLensPositionsProvider availableLensPositionsProvider() {
        if (sdkInfo.isBellowLollipop()) {
            return new V1AvailableLensPositionProvider();
        }
        return new V2AvailableLensPositionProvider(context);
    }

    /**
     * @return The selected {@link CameraProvider} supported by this device.
     */
    public CameraProvider cameraProvider() {
        if (sdkInfo.isBellowLollipop()) {
            return new V1Provider();
        }
        return new V2Provider(context);
    }

}
