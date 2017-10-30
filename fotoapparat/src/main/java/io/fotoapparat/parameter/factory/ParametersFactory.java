package io.fotoapparat.parameter.factory;

import android.support.annotation.NonNull;

import java.util.Collection;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Functions which build {@link Parameters} from given {@link Capabilities} and selector functions.
 */
public class ParametersFactory {

    /**
     * @return new parameters by selecting picture size from given capabilities.
     */
    public static Parameters selectPictureSize(@NonNull Capabilities capabilities,
                                               @NonNull SelectorFunction<Collection<Size>, Size> selector) {
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
                                               @NonNull SelectorFunction<Collection<Size>, Size> selector) {
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
                                             @NonNull SelectorFunction<Collection<FocusMode>, FocusMode> selector) {
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
                                             @NonNull SelectorFunction<Collection<Flash>, Flash> selector) {
        return new Parameters().putValue(
                Parameters.Type.FLASH,
                selector.select(
                        capabilities.supportedFlashModes()
                )
        );
    }

    /**
     * @return new parameters by selecting preview FPS range from given capabilities.
     */
    public static Parameters selectPreviewFpsRange(@NonNull Capabilities capabilities,
                                                   @NonNull SelectorFunction<Collection<Range<Integer>>, Range<Integer>> selector) {
        return new Parameters().putValue(
                Parameters.Type.PREVIEW_FPS_RANGE,
                selector.select(
                        capabilities.supportedPreviewFpsRanges()
                )
        );
    }


    /**
     * @return new parameters by selecting sensor sensitivity from given capabilities.
     */
    public static Parameters selectSensorSensitivity(@NonNull Capabilities capabilities,
                                                     @NonNull SelectorFunction<Range<Integer>, Integer> selector) {
        return new Parameters().putValue(
                Parameters.Type.SENSOR_SENSITIVITY,
                selector.select(
                        capabilities.supportedSensorSensitivityRange()
                )
        );
    }

    /**
     * @param jpegQuality integer (1-100)
     * @return new parameters with a set jpegQuality
     */
    public static Parameters selectJpegQuality(@NonNull Integer jpegQuality) {
        return new Parameters().putValue(
                Parameters.Type.JPEG_QUALITY,
                jpegQuality
        );
    }


}
