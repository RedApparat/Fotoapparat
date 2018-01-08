@file:Suppress("DEPRECATION")

package io.fotoapparat.hardware.metering.convert

import android.graphics.Matrix
import android.graphics.Rect
import android.hardware.Camera
import io.fotoapparat.hardware.metering.FocalRequest
import io.fotoapparat.hardware.metering.PointF
import io.fotoapparat.parameter.Resolution

/**
 * The weight of the camera area.
 * Only 1will be used, so max weight, `1000`, is used.
 */
private const val WEIGHT = 1000

/**
 * From -1000 to 1000
 *
 * @see Camera.Area
 */
private const val CAMERA_BOUNDS_RANGE = 2000f

/**
 * The half dimension size of a focus area.
 *
 * The camera will focus on a maximum area of `(2 * this) ^ 2`.
 */
private const val FOCUS_AREA_HALF_SIZE = 50

/**
 * Converts a [FocalRequest] to a list of [Camera.Area] that the camera should focus at.
 */
internal fun FocalRequest.toFocusAreas(
        displayOrientationDegrees: Int,
        cameraIsMirrored: Boolean
): List<Camera.Area> = listOf(Camera.Area(
        focusBounds(
                displayOrientationDegrees = displayOrientationDegrees.toFloat(),
                cameraIsMirrored = cameraIsMirrored
        ),
        WEIGHT
))

private fun FocalRequest.focusBounds(
        displayOrientationDegrees: Float,
        cameraIsMirrored: Boolean
): Rect = point
        .adjustPointToCameraPreview(
                previewResolution,
                displayOrientationDegrees,
                cameraIsMirrored
        )
        .toBoundsRect()

private fun PointF.adjustPointToCameraPreview(
        visibleResolution: Resolution,
        displayOrientationDegrees: Float,
        cameraIsMirrored: Boolean
): PointF = Matrix()
        .configMatrix(
                visibleResolution,
                displayOrientationDegrees,
                cameraIsMirrored
        )
        .run {
            floatArrayOf(x, y).also {
                mapPoints(it)
            }
        }
        .let {
            PointF(
                    it[0].verifyInBounds(),
                    it[1].verifyInBounds()
            )
        }

/**
 * The point value should mainly be adjusted by `2000 * value - 1000`
 * in order to be in `-1000..1000` range.
 */
private fun Matrix.configMatrix(
        visibleResolution: Resolution,
        displayOrientationDegrees: Float,
        cameraIsMirrored: Boolean
) = apply {
    postScale(
            CAMERA_BOUNDS_RANGE / visibleResolution.width.toFloat(),
            CAMERA_BOUNDS_RANGE / visibleResolution.height.toFloat()
    )
    postTranslate(
            -CAMERA_BOUNDS_RANGE / 2,
            -CAMERA_BOUNDS_RANGE / 2
    )
    postRotate(-displayOrientationDegrees)
    postScale(
            if (cameraIsMirrored) -1f else 1f,
            1f
    )
}

private fun Float.verifyInBounds(): Float {
    return takeIf { it in -1000f..1000f }
            ?: throw IllegalArgumentException("Point value should be between -1000.0 and 1000.0. Was $this")
}

private fun PointF.toBoundsRect() = Rect(
        (x - FOCUS_AREA_HALF_SIZE).ensureAreaBound(),
        (y - FOCUS_AREA_HALF_SIZE).ensureAreaBound(),
        (x + FOCUS_AREA_HALF_SIZE).ensureAreaBound(),
        (y + FOCUS_AREA_HALF_SIZE).ensureAreaBound()
)

private fun Float.ensureAreaBound() = clamp(-1000f, 1000f).toInt()

private fun Float.clamp(min: Float, max: Float): Float = Math.max(min, Math.min(this, max))
