@file:Suppress("DEPRECATION")

package io.fotoapparat.hardware

import android.hardware.Camera
import android.media.MediaRecorder
import android.view.Surface
import androidx.annotation.FloatRange
import io.fotoapparat.capability.Capabilities
import io.fotoapparat.capability.provide.getCapabilities
import io.fotoapparat.characteristic.Characteristics
import io.fotoapparat.characteristic.toCameraId
import io.fotoapparat.coroutines.AwaitBroadcastChannel
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.metering.FocalRequest
import io.fotoapparat.hardware.metering.convert.toFocusAreas
import io.fotoapparat.hardware.orientation.*
import io.fotoapparat.log.Logger
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.parameter.camera.apply.applyInto
import io.fotoapparat.parameter.camera.convert.toCode
import io.fotoapparat.preview.PreviewStream
import io.fotoapparat.result.FocusResult
import io.fotoapparat.result.Photo
import io.fotoapparat.util.FrameProcessor
import io.fotoapparat.util.lineSeparator
import io.fotoapparat.view.Preview
import kotlinx.coroutines.CompletableDeferred
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

typealias PreviewSize = io.fotoapparat.parameter.Resolution

/**
 * Camera.
 */
internal open class CameraDevice(
        private val logger: Logger,
        val characteristics: Characteristics
) {

    private val capabilities = CompletableDeferred<Capabilities>()
    private val cameraParameters = AwaitBroadcastChannel<CameraParameters>()
    private lateinit var previewStream: PreviewStream
    private lateinit var surface: Surface
    private lateinit var camera: Camera

    private var cachedCameraParameters: Camera.Parameters? = null
    private lateinit var displayOrientation: Orientation
    private lateinit var imageOrientation: Orientation
    private lateinit var previewOrientation: Orientation

    /**
     * Opens a connection to a camera.
     */
    open fun open() {
        logger.recordMethod()

        val lensPosition = characteristics.lensPosition
        val cameraId = lensPosition.toCameraId()

        try {
            camera = Camera.open(cameraId)
            capabilities.complete(camera.getCapabilities())
            previewStream = PreviewStream(camera)
        } catch (e: RuntimeException) {
            throw CameraException(
                    message = "Failed to open camera with lens position: $lensPosition and id: $cameraId",
                    cause = e
            )
        }
    }

    /**
     * Closes the connection to a camera.
     */
    open fun close() {
        logger.recordMethod()
        surface.release()
        camera.release()
    }

    /**
     * Starts preview.
     */
    open fun startPreview() {
        logger.recordMethod()

        try {
            camera.startPreview()
        } catch (e: RuntimeException) {
            throw CameraException(
                    message = "Failed to start preview for camera with lens " +
                            "position: ${characteristics.lensPosition} and id: ${characteristics.cameraId}",
                    cause = e
            )
        }
    }

    /**
     * Stops preview.
     */
    open fun stopPreview() {
        logger.recordMethod()

        camera.stopPreview()
    }

    /**
     * Unlock camera.
     */
    open fun unlock() {
        logger.recordMethod()

        camera.unlock()
    }

    /**
     * Lock camera.
     */
    open fun lock() {
        logger.recordMethod()

        camera.lock()
    }

    /**
     * Invokes a still photo capture action.
     *
     * @return The captured photo.
     */
    open fun takePhoto(): Photo {
        logger.recordMethod()

        return camera.takePhoto(imageOrientation.degrees)
    }

    /**
     * Returns the [Capabilities] of the camera.
     */
    open suspend fun getCapabilities(): Capabilities {
        logger.recordMethod()

        return capabilities.await()
    }

    /**
     * Returns the [CameraParameters] used.
     */
    open suspend fun getParameters(): CameraParameters {
        logger.recordMethod()

        return cameraParameters.getValue()
    }

    /**
     * Updates the desired camera parameters.
     */
    open suspend fun updateParameters(cameraParameters: CameraParameters) {
        logger.recordMethod()

        this.cameraParameters.send(cameraParameters)

        logger.log("New camera parameters are: $cameraParameters")

        cameraParameters.applyInto(cachedCameraParameters ?: camera.parameters)
                .cacheLocally()
                .setInCamera()
    }

    /**
     * Updates the frame processor.
     */
    open fun updateFrameProcessor(frameProcessor: FrameProcessor?) {
        logger.recordMethod()

        previewStream.updateProcessorSafely(frameProcessor)
    }

    /**
     * Sets the current orientation of the display.
     */
    open fun setDisplayOrientation(orientationState: OrientationState) {
        logger.recordMethod()

        imageOrientation = computeImageOrientation(
                deviceOrientation = orientationState.deviceOrientation,
                cameraOrientation = characteristics.cameraOrientation,
                cameraIsMirrored = characteristics.isMirrored
        )

        displayOrientation = computeDisplayOrientation(
                screenOrientation = orientationState.screenOrientation,
                cameraOrientation = characteristics.cameraOrientation,
                cameraIsMirrored = characteristics.isMirrored
        )

        previewOrientation = computePreviewOrientation(
                screenOrientation = orientationState.screenOrientation,
                cameraOrientation = characteristics.cameraOrientation,
                cameraIsMirrored = characteristics.isMirrored
        )

        logger.log("Orientations: $lineSeparator" +
                "Screen orientation (preview) is: ${orientationState.screenOrientation}. " + lineSeparator +
                "Camera sensor orientation is always at: ${characteristics.cameraOrientation}. " + lineSeparator +
                "Camera is " + if (characteristics.isMirrored) "mirrored." else "not mirrored."
        )

        logger.log("Orientation adjustments: $lineSeparator" +
                "Image orientation will be adjusted by: ${imageOrientation.degrees} degrees. " + lineSeparator +
                "Display orientation will be adjusted by: ${displayOrientation.degrees} degrees. " + lineSeparator +
                "Preview orientation will be adjusted by: ${previewOrientation.degrees} degrees."
        )

        previewStream.frameOrientation = previewOrientation
        camera.setDisplayOrientation(displayOrientation.degrees)
    }

    /**
     * Changes zoom level of the camera. Must be called only if zoom is supported.
     *
     * @param level normalized zoom level. Value in range [0..1].
     */
    open fun setZoom(@FloatRange(from = 0.0, to = 1.0) level: Float) {
        logger.recordMethod()

        setZoomSafely(level)
    }

    /**
     * Performs auto focus. This is a blocking operation which returns the result of the operation
     * when auto focus completes.
     */
    open fun autoFocus(): FocusResult {
        logger.recordMethod()

        return camera.focusSafely()
    }

    /**
     * Sets the point where the focus & exposure metering will happen.
     */
    open suspend fun setFocalPoint(focalRequest: FocalRequest) {
        logger.recordMethod()

        if (capabilities.await().canSetFocusingAreas()) {
            camera.updateFocusingAreas(focalRequest)
        }
    }

    /**
     * Clears the point where the focus & exposure will happen.
     */
    open fun clearFocalPoint() {
        logger.recordMethod()

        camera.clearFocusingAreas()
    }

    /**
     * Sets the desired surface on which the camera's preview will be displayed.
     */
    @Throws(IOException::class)
    open fun setDisplaySurface(preview: Preview) {
        logger.recordMethod()

        surface = camera.setDisplaySurface(preview)
    }

    /**
     * Attaches the camera to the [MediaRecorder].
     */
    open fun attachRecordingCamera(mediaRecorder: MediaRecorder) {
        logger.recordMethod()

        mediaRecorder.setCamera(camera)
    }

    /**
     * Returns the [Resolution] of the displayed preview.
     */
    open fun getPreviewResolution(): Resolution {
        logger.recordMethod()

        val previewResolution = camera.getPreviewResolution(previewOrientation)

        logger.log("Preview resolution is: $previewResolution")

        return previewResolution
    }

    private fun setZoomSafely(@FloatRange(from = 0.0, to = 1.0) level: Float) {
        try {
            setZoomUnsafe(level)
        } catch (e: Exception) {
            logger.log("Unable to change zoom level to " + level + " e: " + e.message)
        }
    }

    private fun setZoomUnsafe(@FloatRange(from = 0.0, to = 1.0) level: Float) {
        (cachedCameraParameters ?: camera.parameters)
                .apply {
                    zoom = (maxZoom * level).toInt()
                }
                .cacheLocally()
                .setInCamera()
    }

    private fun Camera.Parameters.cacheLocally() = apply {
        cachedCameraParameters = this
    }

    private fun Camera.Parameters.setInCamera() = apply {
        camera.parameters = this
    }

    private fun Camera.focusSafely(): FocusResult {
        val latch = CountDownLatch(1)

        try {
            autoFocus { _, _ -> latch.countDown() }
        } catch (e: Exception) {
            logger.log("Failed to perform autofocus using device ${characteristics.cameraId} e: ${e.message}")

            return FocusResult.UnableToFocus
        }

        try {
            latch.await(AUTOFOCUS_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            // Do nothing
        }

        return FocusResult.Focused
    }

    private suspend fun Camera.updateFocusingAreas(focalRequest: FocalRequest) {
        val focusingAreas = focalRequest.toFocusAreas(
                displayOrientationDegrees = displayOrientation.degrees,
                cameraIsMirrored = characteristics.isMirrored
        )

        parameters = parameters.apply {
            with(capabilities.await()) {
                if (maxMeteringAreas > 0) {
                    meteringAreas = focusingAreas
                }

                if (maxFocusAreas > 0) {
                    if (focusModes.contains(FocusMode.Auto)) {
                        focusMode = FocusMode.Auto.toCode()
                    }
                    focusAreas = focusingAreas
                }
            }
        }
    }

    private fun Camera.clearFocusingAreas() {
        parameters = parameters.apply {
            meteringAreas = null
            focusAreas = null
        }
    }

}

private const val AUTOFOCUS_TIMEOUT_SECONDS = 3L

private fun Camera.takePhoto(imageRotation: Int): Photo {
    val latch = CountDownLatch(1)
    val photoReference = AtomicReference<Photo>()

    takePicture(
            null,
            null,
            null,
            Camera.PictureCallback { data, _ ->
                photoReference.set(
                        Photo(data, imageRotation)
                )

                latch.countDown()
            }
    )

    latch.await()

    return photoReference.get()
}

@Throws(IOException::class)
private fun Camera.setDisplaySurface(
        preview: Preview
): Surface = when (preview) {
    is Preview.Texture -> preview.surfaceTexture
            .also(this::setPreviewTexture)
            .let(::Surface)

    is Preview.Surface -> preview.surfaceHolder
            .also(this::setPreviewDisplay)
            .surface
}

private fun Camera.getPreviewResolution(previewOrientation: Orientation): Resolution {
    return parameters.previewSize
            .run {
                PreviewSize(width, height)
            }
            .run {
                when (previewOrientation) {
                    is Orientation.Vertical -> this
                    is Orientation.Horizontal -> flipDimensions()
                }
            }
}

private fun Capabilities.canSetFocusingAreas(): Boolean =
        maxMeteringAreas > 0 || maxFocusAreas > 0
