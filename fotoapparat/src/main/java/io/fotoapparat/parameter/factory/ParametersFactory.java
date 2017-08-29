package io.fotoapparat.parameter.factory;

import android.support.annotation.NonNull;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Functions which build {@link Parameters} from given {@link Capabilities} and selector functions.
 */
public class ParametersFactory {

    /**
     * @return new parameters by selecting picture size from given capabilities.
     */
    public static Parameters selectPictureSize(@NonNull Capabilities capabilities,
                                               @NonNull SelectorFunction<Size> selector) {
        return new Parameters().putValue(
                Parameters.Type.PICTURE_SIZE,
                selector.select(
                        capabilities.supportedPictureSizes()
                )
        );
    }

    /**
     * @return new parameters by selecting preview size from given capabilities.
     */
    public static Parameters selectPreviewSize(@NonNull Capabilities capabilities,
                                               @NonNull SelectorFunction<Size> selector) {
        return new Parameters().putValue(
                Parameters.Type.PREVIEW_SIZE,
                selector.select(
                        capabilities.supportedPreviewSizes()
                )
        );
    }

    /**
     * @return new parameters by selecting focus mode from given capabilities.
     */
    public static Parameters selectFocusMode(@NonNull Capabilities capabilities,
                                             @NonNull SelectorFunction<FocusMode> selector) {
        return new Parameters().putValue(
                Parameters.Type.FOCUS_MODE,
                selector.select(
                        capabilities.supportedFocusModes()
                )
        );
    }

    /**
     * @return new parameters by selecting flash mode from given capabilities.
     */
    public static Parameters selectFlashMode(@NonNull Capabilities capabilities,
                                             @NonNull SelectorFunction<Flash> selector) {
        return new Parameters().putValue(
                Parameters.Type.FLASH,
                selector.select(
                        capabilities.supportedFlashModes()
                )
        );
    }

}
