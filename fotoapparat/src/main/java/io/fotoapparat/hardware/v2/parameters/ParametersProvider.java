package io.fotoapparat.hardware.v2.parameters;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;

import static io.fotoapparat.parameter.Parameters.Type.FLASH;
import static io.fotoapparat.parameter.Parameters.Type.FOCUS_MODE;
import static io.fotoapparat.parameter.Parameters.Type.JPEG_QUALITY;
import static io.fotoapparat.parameter.Parameters.Type.PICTURE_SIZE;
import static io.fotoapparat.parameter.Parameters.Type.PREVIEW_FPS_RANGE;
import static io.fotoapparat.parameter.Parameters.Type.PREVIEW_SIZE;
import static io.fotoapparat.parameter.Parameters.Type.SENSOR_SENSITIVITY;

/**
 * Manages the parameters of a {@link io.fotoapparat.hardware.CameraDevice}.
 */
public class ParametersProvider implements ParametersOperator {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private Parameters selectedParameters;

    @Override
    public void updateParameters(Parameters selectedParameters) {
        this.selectedParameters = selectedParameters;
        countDownLatch.countDown();
    }

    /**
     * Returns the last updated parameters. This will block the calling thread until the parameters
     * have been obtained.
     *
     * @return the last updated parameters.
     */
    private Parameters getSelectedParameters() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            // Do nothing
        }
        return selectedParameters;
    }

    /**
     * Returns the flash firing mode of the camera.
     *
     * @return The flash firing mode.
     */
    public Flash getFlash() {
        return getSelectedParameters().getValue(FLASH);
    }

    /**
     * Returns the focus mode of the camera.
     *
     * @return The focus mode.
     */
    public FocusMode getFocus() {
        return getSelectedParameters().getValue(FOCUS_MODE);
    }

    /**
     * Returns the still picture capture size.
     *
     * @return The size.
     */
    public Size getStillCaptureSize() {
        return getSelectedParameters().getValue(PICTURE_SIZE);
    }

    /**
     * Returns the preview stream size.
     *
     * @return The size.
     */
    public Size getPreviewSize() {
        return getSelectedParameters().getValue(PREVIEW_SIZE);
    }

    /**
     * The aspect ratio (width/height) based on the still picture capture size.
     *
     * @return The aspect ratio (width/height).
     */
    public float getStillCaptureAspectRatio() {
        return getStillCaptureSize().getAspectRatio();
    }

    /**
     * Returns the preview FPS range. Note that values in range multiplied by 1000.
     *
     * @return The preview FPS range.
     */
    public Range<Integer> getPreviewFpsRange() {
        return getSelectedParameters().getValue(PREVIEW_FPS_RANGE);
    }

    /**
     * Returns the sensor sensitivity (ISO).
     *
     * @return The sensor sensitivity.
     */
    public Integer getSensorSensitivity() {
        return selectedParameters.getValue(SENSOR_SENSITIVITY);
    }

    /**
     * Returns the jpeg quality
     *
     * @return  The jpeg quality (1-100)
     */
    public Integer getJpegQuality() {
        return selectedParameters.getValue(JPEG_QUALITY);
    }
}
