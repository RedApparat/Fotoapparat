package io.fotoapparat.hardware.v1;

import io.fotoapparat.hardware.v1.capabilities.FlashCapability;
import io.fotoapparat.hardware.v1.capabilities.FocusCapability;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;

/**
 * Converts {@link Parameters} to {@link CameraParametersDecorator}.
 */
@SuppressWarnings("deprecation")
public class ParametersConverter {

    /**
     * Converts {@link Parameters} to {@link CameraParametersDecorator}.
     *
     * @param parameters parameters which should be converted.
     * @param output     output value. It is required because of C-style API in Camera v1.
     * @return same object which was passed as {@code output}, but filled with new parameters.
     */
    public CameraParametersDecorator convert(Parameters parameters,
                                             CameraParametersDecorator output) {
        for (Parameters.Type storedType : parameters.storedTypes()) {
            applyParameter(
                    storedType,
                    parameters,
                    output
            );
        }

        return output;
    }

    private void applyParameter(Parameters.Type type,
                                Parameters input,
                                CameraParametersDecorator output) {
        switch (type) {
            case FOCUS_MODE:
                applyFocusMode(
                        (FocusMode) input.getValue(type),
                        output
                );
                break;
            case FLASH:
                applyFlash(
                        (Flash) input.getValue(type),
                        output
                );
                break;
            case PICTURE_SIZE:
                applyPictureSize(
                        (Size) input.getValue(type),
                        output
                );
                break;
            case PREVIEW_SIZE:
                applyPreviewSize(
                        (Size) input.getValue(type),
                        output
                );
                break;
            case PREVIEW_FPS_RANGE:
                applyPreviewFpsRange(
                        (Range<Integer>) input.getValue(type),
                        output
                );
                break;
            case SENSOR_SENSITIVITY:
                applySensorSensitivity(
                        (Integer) input.getValue(type),
                        output
                );
                break;
        }
    }

    private void applyPreviewSize(Size size,
                                  CameraParametersDecorator output) {
        output.setPreviewSize(size.width, size.height);
    }

    private void applyPictureSize(Size size,
                                  CameraParametersDecorator output) {
        output.setPictureSize(size.width, size.height);
    }

    private void applyFlash(Flash flash,
                            CameraParametersDecorator output) {
        output.setFlashMode(
                FlashCapability.toCode(flash)
        );
    }

    private void applyFocusMode(FocusMode focusMode,
                                CameraParametersDecorator output) {
        output.setFocusMode(
                FocusCapability.toCode(focusMode)
        );
    }

    private void applyPreviewFpsRange(Range<Integer> fpsRange,
                                      CameraParametersDecorator output) {
        output.setPreviewFpsRange(
                fpsRange.lowest(), fpsRange.highest()
        );
    }

    private void applySensorSensitivity(Integer value,
                                        CameraParametersDecorator output) {
        output.setSensorSensitivityValue(value);
    }

}
