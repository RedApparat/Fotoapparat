package io.fotoapparat.hardware.v1;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides {@link Camera.Parameters} with methods to extract additional camera parameters.
 */
@SuppressWarnings("deprecation")
public class CameraParametersProvider {

    /* Raw camera params keys */
    private static final String[] RAW_ISO_SUPPORTED_VALUES_KEYS = {
            "iso-values", "iso-mode-values", "iso-speed-values", "nv-picture-iso-values"
    };
    private static final String[] RAW_ISO_CURRENT_VALUE_KEYS = {
            "iso", "iso-speed", "nv-picture-iso"
    };

    private Camera.Parameters cameraParameters;

    public CameraParametersProvider(Camera.Parameters cameraParameters) {
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

    /**
     * Returns set of ISO values, that camera supports.
     *
     * @return the set of supported ISO values.
     */
    @NonNull
    public Set<Integer> getSensorSensitivityValues() {
        final Set<Integer> isoValuesSet = new HashSet<>();

        String[] rawValues = extractRawCameraValues(RAW_ISO_SUPPORTED_VALUES_KEYS);

        // Should log that result is nullable or not?
        if (rawValues != null) {
            for (String value : rawValues) {
                try {
                    isoValuesSet.add(Integer.valueOf(value.trim()));
                } catch (NumberFormatException e) {
                    // Found not number option. Skip it.
                }
            }
        }

        return isoValuesSet;
    }

    @Nullable
    private String[] extractRawCameraValues(@NonNull String[] keys) {
        for (String key: keys) {
            String[] rawValues  = extractRawCameraValues(key);
            if (rawValues != null) {
                return rawValues;
            }
        }

        return null;
    }

    @Nullable
    private String[] extractRawCameraValues(@NonNull String key) {
        String rawValues = cameraParameters.get(key);
        if (rawValues != null) {
            return rawValues.split(",");
        } else {
            return null;
        }
    }
}
