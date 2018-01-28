@file:Suppress("DEPRECATION")

package io.fotoapparat.parameter.camera.apply

import android.hardware.Camera
import io.fotoapparat.parameter.*
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.parameter.camera.convert.toCode


/**
 * Applies a new set of [CameraParameters] to existing [Camera.Parameters].
 *
 * @receiver The existing [Camera.Parameters]
 * @param parameters A new set of [CameraParameters].
 *
 * @return Same [Camera.Parameters] object which was passed, but filled with new parameters.
 */
internal fun CameraParameters.applyInto(parameters: Camera.Parameters): Camera.Parameters {
    this tryApplyInto parameters
    return parameters
}

private infix fun CameraParameters.tryApplyInto(parameters: Camera.Parameters) {
    flashMode applyInto parameters
    focusMode applyInto parameters
    jpegQuality applyJpegQualityInto parameters
    exposureCompensation applyExposureCompensationInto parameters
    antiBandingMode applyInto parameters
    previewFpsRange applyInto parameters
    previewResolution applyPreviewInto parameters
    sensorSensitivity applySensitivityInto parameters
    pictureResolution applyPictureResolutionInto parameters
}

private infix fun Flash.applyInto(parameters: Camera.Parameters) {
    parameters.flashMode = toCode()
}

private infix fun FocusMode.applyInto(parameters: Camera.Parameters) {
    parameters.focusMode = toCode()
}

private infix fun Int.applyJpegQualityInto(parameters: Camera.Parameters) {
    parameters.jpegQuality = this
}

private infix fun Int.applyExposureCompensationInto(parameters: Camera.Parameters) {
    parameters.exposureCompensation = this
}

private infix fun AntiBandingMode.applyInto(parameters: Camera.Parameters) {
    parameters.antibanding = toCode()
}

private infix fun FpsRange.applyInto(parameters: Camera.Parameters) {
    parameters.setPreviewFpsRange(min, max)
}

private infix fun Int?.applySensitivityInto(parameters: Camera.Parameters) {
    this?.let { sensitivity ->
        parameters.findSensitivityKey()?.let { key ->
            parameters.set(key, sensitivity)
        }
    }
}

private infix fun Resolution.applyPreviewInto(parameters: Camera.Parameters) {
    parameters.setPreviewSize(width, height)
}

private infix fun Resolution.applyPictureResolutionInto(parameters: Camera.Parameters) {
    parameters.setPictureSize(width, height)
}

private fun Camera.Parameters.findSensitivityKey() = currentSensitivityKeys.find { get(it) != null }

private val currentSensitivityKeys = listOf("iso", "iso-speed", "nv-picture-iso")
