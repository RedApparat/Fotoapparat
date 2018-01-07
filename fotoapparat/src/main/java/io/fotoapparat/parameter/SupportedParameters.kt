@file:Suppress("DEPRECATION")

package io.fotoapparat.parameter

import android.hardware.Camera
import io.fotoapparat.parameter.extract.extractRawCameraValues
import io.fotoapparat.util.toInts

/**
 * Provides the supported [Camera.Parameters] with defaults where needed.
 */
internal class SupportedParameters(
        private val cameraParameters: Camera.Parameters
) {

    /**
     * @see Camera.Parameters.getSupportedFlashModes
     */
    val flashModes: List<String> by lazy {
        cameraParameters.supportedFlashModes ?: listOf(Camera.Parameters.FLASH_MODE_OFF)
    }

    /**
     * @see Camera.Parameters.getSupportedFocusModes
     */
    val focusModes: List<String> by lazy {
        cameraParameters.supportedFocusModes
    }

    /**
     * @see Camera.Parameters.getSupportedPreviewSizes
     */
    val previewResolutions: List<Camera.Size> by lazy {
        cameraParameters.supportedPreviewSizes
    }

    /**
     * @see Camera.Parameters.getSupportedPictureSizes
     */
    val pictureResolutions: List<Camera.Size> by lazy {
        cameraParameters.supportedPictureSizes
    }

    /**
     * @see Camera.Parameters.getSupportedPreviewFpsRange
     */
    val supportedPreviewFpsRanges: List<IntArray> by lazy {
        cameraParameters.supportedPreviewFpsRange
    }

    /**
     * @return The list of supported sensitivities (ISO) by the camera.
     */
    val sensorSensitivities: List<Int> by lazy {
        cameraParameters.extractRawCameraValues(supportedSensitivitiesKeys).toInts()
    }

    /**
     * @see Camera.Parameters.isZoomSupported
     */
    val supportedZoom by lazy {
        cameraParameters.isZoomSupported
    }

    /**
     * @see Camera.Parameters.isSmoothZoomSupported
     */
    val supportedSmoothZoom by lazy {
        cameraParameters.isSmoothZoomSupported
    }

    /**
     * @see Camera.Parameters.getSupportedAntibanding
     */
    val supportedAutoBandingModes by lazy {
        cameraParameters.supportedAntibanding ?: listOf(Camera.Parameters.ANTIBANDING_OFF)
    }

    /**
     * @return A [IntRange] of supported jpeg qualities that the camera can take photos.
     */
    val jpegQualityRange by lazy {
        IntRange(0, 100)
    }

    /**
     * @see Camera.Parameters.getMaxNumFocusAreas
     */
    val maxNumFocusAreas by lazy {
        cameraParameters.maxNumFocusAreas
    }

    /**
     * @see Camera.Parameters.getMaxNumMeteringAreas
     */
    val maxNumMeteringAreas by lazy {
        cameraParameters.maxNumMeteringAreas
    }
}

private val supportedSensitivitiesKeys = listOf("iso-values", "iso-mode-values", "iso-speed-values", "nv-picture-iso-values")
