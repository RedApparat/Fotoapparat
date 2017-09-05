package io.fotoapparat.hardware.v1;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Decorator for {@link Camera.Parameters} with methods
 * for getting and settings additional camera parameters.
 */
@SuppressWarnings("deprecation")
public class CameraParametersDecorator {

    /* Raw camera params keys */
    private static final String[] RAW_ISO_SUPPORTED_VALUES_KEYS = {
            "iso-values", "iso-mode-values", "iso-speed-values", "nv-picture-iso-values"
    };
    private static final String[] RAW_ISO_CURRENT_VALUE_KEYS = {
            "iso", "iso-speed", "nv-picture-iso"
    };

    private Camera.Parameters cameraParameters;

    public Camera.Parameters getCameraParameters() {
        return cameraParameters;
    }

    public CameraParametersDecorator(Camera.Parameters cameraParameters) {
        this.cameraParameters = cameraParameters;
    }

    public boolean isZoomSupported() {
        return cameraParameters.isZoomSupported();
    }

    public List<Size> getSupportedPreviewSizes() {
        return cameraParameters.getSupportedPreviewSizes();
    }

    public List<Size> getSupportedPictureSizes() {
        return cameraParameters.getSupportedPictureSizes();
    }

    public List<String> getSupportedFlashModes() {
        return cameraParameters.getSupportedFlashModes();
    }

    public List<String> getSupportedFocusModes() {
        return cameraParameters.getSupportedFocusModes();
    }

    public List<int[]> getSupportedPreviewFpsRange() {
        return cameraParameters.getSupportedPreviewFpsRange();
    }

    public void setPreviewSize(int width, int height) {
        cameraParameters.setPreviewSize(width, height);
    }

    public void setPictureSize(int width, int height) {
        cameraParameters.setPictureSize(width, height);
    }

    public void setFlashMode(String flash) {
        cameraParameters.setFlashMode(flash);
    }

    public void setFocusMode(String focusMode) {
        cameraParameters.setFocusMode(focusMode);
    }

    public void setPreviewFpsRange(int min, int max) {
        cameraParameters.setPreviewFpsRange(min, max);
    }

    /**
     * Returns set of ISO values, that camera supports.
     *
     * @return the set of supported ISO values.
     */
    @NonNull
    public Set<Integer> getSensorSensitivityValues() {
        String[] rawValues = extractRawCameraValues(RAW_ISO_SUPPORTED_VALUES_KEYS);
        return convertParamsToInts(rawValues);
    }

    /**
     * Sets ISO value for camera.
     */
    @NonNull
    public void setSensorSensitivityValue(int isoValue) {
        String isoKey = findExistingKey(RAW_ISO_CURRENT_VALUE_KEYS);
        if (isoKey != null) {
            cameraParameters.set(isoKey, isoValue);
        }
    }

    @Nullable
    private String findExistingKey(@NonNull String[] keys) {
        for (String key: keys) {
            if (cameraParameters.get(key) != null) {
                return key;
            }
        }

        return null;
    }

    @Nullable
    private String[] extractRawCameraValues(@NonNull String[] keys) {
        for (String key: keys) {
            String[] rawValues  = extractStringValuesFromParams(key);
            if (rawValues != null) {
                return rawValues;
            }
        }

        return null;
    }

    @Nullable
    private String[] extractStringValuesFromParams(@NonNull String key) {
        String rawValues = cameraParameters.get(key);
        if (rawValues != null) {
            return rawValues.split(",");
        } else {
            return null;
        }
    }

    @NonNull
    private Set<Integer> convertParamsToInts(@Nullable String[] values) {
        Set<Integer> integerValues = new HashSet<>();
        if (values != null) {
            for (String value : values) {
                try {
                    integerValues.add(Integer.valueOf(value.trim()));
                } catch (NumberFormatException e) {
                    // Found not number option. Skip it.
                }
            }
        }
        return integerValues;
    }
}
