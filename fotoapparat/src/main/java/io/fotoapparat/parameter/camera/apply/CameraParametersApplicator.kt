package io.fotoapparat.parameter.camera.apply

import android.hardware.Camera
import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.parameter.camera.convert.toCode


/**
 * Applies a new set of [CameraParameters] to existing [Camera.Parameters].
 *
 * @receiver The existing [Camera.Parameters]
 * @param newParameters A new set of [CameraParameters].
 *
 * @return Same [Camera.Parameters] object which was passed, but filled with new parameters.
 */
internal fun Camera.Parameters.applyNewParameters(newParameters: CameraParameters): Camera.Parameters {
    newParameters tryApplyInto this
    return this
}

private infix fun CameraParameters.tryApplyInto(parameters: Camera.Parameters) {
    flashMode applyInto parameters
    focusMode applyInto parameters
    previewFpsRange applyInto parameters
    sensorSensitivity applyInto parameters
    previewResolution applyPreviewInto parameters
    pictureResolution applyPictureResolutionInto parameters
}

private infix fun Flash.applyInto(parameters: Camera.Parameters) {
    parameters.flashMode = toCode()
}

private infix fun FocusMode.applyInto(parameters: Camera.Parameters) {
    parameters.focusMode = toCode()
}

private infix fun FpsRange.applyInto(parameters: Camera.Parameters) {
    parameters.setPreviewFpsRange(min, max)
}

private infix fun Int?.applyInto(parameters: Camera.Parameters) {
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
