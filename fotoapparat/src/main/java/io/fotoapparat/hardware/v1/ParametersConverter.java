package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

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
    public CameraParametersDecorator toPlatformParameters(Parameters parameters,
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

    /**
     * Converts {@link Camera.Parameters} to {@link Parameters}.
     *
     * @param platformParameters parameters which should be converted.
     * @return Converted parameters object
     */
    public Parameters fromPlatformParameters(CameraParametersDecorator platformParameters) {
        Parameters parameters = new Parameters();

        FocusMode focusMode = FocusCapability.toFocusMode(platformParameters.getFocusMode());
        parameters.putValue(Parameters.Type.FOCUS_MODE, focusMode);

        Flash flash = FlashCapability.toFlash(platformParameters.getFlashMode());
        parameters.putValue(Parameters.Type.FLASH, flash);

        Camera.Size platformSize = platformParameters.getPictureSize();
        Size pictureSize = new Size(platformSize.width, platformSize.height);
        parameters.putValue(Parameters.Type.PICTURE_SIZE, pictureSize);

        Camera.Size platformPreviewSize = platformParameters.getPreviewSize();
        Size previewSize = new Size(platformPreviewSize.width, platformPreviewSize.height);
        parameters.putValue(Parameters.Type.PREVIEW_SIZE, previewSize);

        return parameters;
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
                        getRange(type, input),
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

    @SuppressWarnings("unchecked")
    private Range<Integer> getRange(Parameters.Type type, Parameters input) {
        return (Range<Integer>) input.getValue(type);
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
