package io.fotoapparat.hardware.v2.capabilities;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Range;

import java.util.HashSet;
import java.util.Set;

import io.fotoapparat.parameter.Size;

/**
 * Wrapper around api's {@link CameraCharacteristics}
 */
@SuppressWarnings("ConstantConditions")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Characteristics {

    private final CameraCharacteristics cameraCharacteristics;

    public Characteristics(CameraCharacteristics cameraCharacteristics) {
        this.cameraCharacteristics = cameraCharacteristics;
    }

    @NonNull
    private static Set<Size> convertSizes(android.util.Size[] availableSizes) {
        HashSet<Size> sizesSet = new HashSet<>(availableSizes.length);

        for (android.util.Size size : availableSizes) {
            sizesSet.add(new Size(size.getWidth(), size.getHeight()));
        }

        return sizesSet;
    }

    /**
     * Whether this camera's lens is facing the front side, relatively the device's screen.
     *
     * @return {@code true} if the camera's lens is facing front.
     */
    public boolean isFrontFacingLens() {
        return cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraMetadata.LENS_FACING_FRONT;
    }

    /**
     * Whether this camera device has a flash unit.
     *
     * @return {@code true} if the camera's lens has a flash unit.
     */
    public boolean isFlashAvailable() {
        return cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
    }

    /**
     * Whether this lens has fixed focus.
     *
     * @return {@code true} if the camera's lens has fixed focus.
     */
    public boolean isFixedFocusLens() {
        Float focusDistance = cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        return focusDistance == 0f;
    }

    /**
     * Informs whether or not the camera has legacy hardware.
     *
     * @return {@code true} if the camera's lens has legacy hardware.
     */
    public boolean isLegacyDevice() {
        int hardwareLevel = cameraCharacteristics
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
        return cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
    }

    /**
     * List of auto-exposure modes that are supported by this camera device.
     *
     * @return The list of the exposure modes. Range of valid values:
     * Any value listed in {@link android.hardware.camera2.CaptureRequest#CONTROL_AE_MODE
     * android.control.aeMode}.
     */
    public int[] autoExposureModes() {
        return cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
    }

    /**
     * List of auto-focus (AF) modes that are supported by this camera device.
     *
     * @return The list of the focus modes. Range of valid values:
     * Any value listed in {@link android.hardware.camera2.CaptureRequest#CONTROL_AF_MODE
     * android.control.afMode}.
     */
    public int[] autoFocusModes() {
        return cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
    }

    /**
     * List of JPEG sizes that this camera device can capture.
     *
     * @return The set of the supported sizes.
     */
    public Set<Size> getJpegOutputSizes() {
        android.util.Size[] outputSizes = cameraCharacteristics
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                .getOutputSizes(ImageFormat.JPEG);

        return convertSizes(outputSizes);
    }

    /**
     * List of sizes that this camera device can export to a stream.
     *
     * @return The set of the sizes which can be outputted to surface. Note that not all of those
     * size can be drawn, so additional filtering is required.
     */
    public Set<Size> getSurfaceOutputSizes() {
        android.util.Size[] outputSizes = cameraCharacteristics
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                .getOutputSizes(SurfaceTexture.class);

        return convertSizes(outputSizes);
    }

    Range<Integer> getSensorSensitivityRange() {
         return cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE);
    }
}
