package io.fotoapparat.hardware.v2.capabilities;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Wrapper around api's {@link CameraCharacteristics}
 */
@SuppressWarnings("ConstantConditions")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Characteristics {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final CameraManager manager;

    private CameraCharacteristics cameraCharacteristics;

    public Characteristics(CameraManager manager) {
        this.manager = manager;
    }

    /**
     * Sets the id of currently open camera.
     *
     * @param cameraId the device camera id
     * @throws CameraAccessException if the camera device has been disconnected.
     */
    public void setCameraId(String cameraId) throws CameraAccessException {
        cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
        countDownLatch.countDown();
    }

    /**
     * Returns synchronously the camera characteristics for a given camera id.
     *
     * @return the {@link CameraCharacteristics}
     */
    private CameraCharacteristics getCameraCharacteristics() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            // Do nothing
        }
        return cameraCharacteristics;
    }

    /**
     * Whether this camera device has a flash unit.
     *
     * @return {@code true} if the camera's lens has a flash unit.
     */
    public boolean isFlashAvailable() {
        return getCameraCharacteristics().get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
    }

    /**
     * Informs whether or not the camera has legacy hardware.
     *
     * @return {@code true} if the camera's lens has legacy hardware.
     */
    public boolean isLegacyDevice() {
        int hardwareLevel = getCameraCharacteristics()
                .get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);

        return hardwareLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY;
    }

    /**
     * Clockwise angle through which the output image needs to be rotated to be
     * upright on the device screen in its native orientation.
     *
     * @return The angle degrees.
     */
    public int getSensorOrientation() {
        return getCameraCharacteristics().get(CameraCharacteristics.SENSOR_ORIENTATION);
    }

    /**
     * List of auto-exposure modes that are supported by this camera device.
     *
     * @return The list of the exposure modes. Range of valid values:
     * Any value listed in {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_MODE
     * android.control.aeMode}.
     */
    public int[] autoExposureModes() {
        return getCameraCharacteristics().get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
    }

    /**
     * List of auto-focus (AF) modes that are supported by this camera device.
     *
     * @return The list of the focus modes. Range of valid values:
     * Any value listed in {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE
     * android.control.afMode}.
     */
    public int[] autoFocusModes() {
        return getCameraCharacteristics().get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
    }

    /**
     * List of JPEG sizes that this camera device can capture.
     *
     * @return The list of the supported sizes.
     */
    public List<Size> getJpegOutputSizes() {
        Size[] outputSizes = getCameraCharacteristics()
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                .getOutputSizes(ImageFormat.JPEG);
        return Arrays.asList(outputSizes);
    }

    /**
     * List of sizes that this camera device can export to a stream.
     *
     * @return The list of the supported sizes.
     */
    public List<Size> getPreviewSizes() {
        Size[] outputSizes = getCameraCharacteristics()
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                .getOutputSizes(SurfaceTexture.class);

        List<Size> filteredOutputSizes = new ArrayList<>();
        for (Size outputSize : outputSizes) {
            if (outputSize.getWidth() <= PreviewSizeInfo.MAX_PREVIEW_WIDTH && outputSize.getHeight() <= PreviewSizeInfo.MAX_PREVIEW_HEIGHT) {
                filteredOutputSizes.add(outputSize);
            }
        }

        return filteredOutputSizes;
    }
}
