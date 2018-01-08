@file:Suppress("DEPRECATION")

package io.fotoapparat.hardware

import android.hardware.Camera
import android.media.MediaRecorder
import android.support.annotation.FloatRange
import android.view.Surface
import io.fotoapparat.capability.Capabilities
import io.fotoapparat.capability.provide.getCapabilities
import io.fotoapparat.characteristic.Characteristics
import io.fotoapparat.characteristic.toCameraId
import io.fotoapparat.coroutines.AwaitBroadcastChannel
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.metering.FocalRequest
import io.fotoapparat.hardware.metering.convert.toFocusAreas
import io.fotoapparat.hardware.orientation.computeDisplayOrientation
import io.fotoapparat.hardware.orientation.computeImageOrientation
import io.fotoapparat.log.Logger
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.parameter.camera.CameraParameters
import io.fotoapparat.parameter.camera.apply.applyNewParameters
import io.fotoapparat.parameter.camera.convert.toCode
import io.fotoapparat.preview.Frame
import io.fotoapparat.preview.PreviewStream
import io.fotoapparat.result.FocusResult
import io.fotoapparat.result.Photo
import io.fotoapparat.view.Preview
import kotlinx.coroutines.experimental.CompletableDeferred
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

    private var cachedZoomParameters: Camera.Parameters? = null
    private var displayRotation = 0
    var imageRotation = 0

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

        return camera.takePhoto(imageRotation)
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

        camera.updateParameters(cameraParameters)
    }

    /**
     * Updates the frame processor.
     */
    open fun updateFrameProcessor(frameProcessor: ((Frame) -> Unit)?) {
        logger.recordMethod()

        previewStream.updateProcessorSafely(frameProcessor)
    }

    /**
     * Sets the current orientation of the display.
     */
    open fun setDisplayOrientation(degrees: Int) {
        logger.recordMethod()

        imageRotation = computeImageOrientation(
                degrees = degrees,
                characteristics = characteristics
        )

        displayRotation = computeDisplayOrientation(
                degrees = degrees,
                characteristics = characteristics
        )

        logger.log("Image Rotation is: $imageRotation. Display rotation is: $displayRotation")

        previewStream.frameOrientation = imageRotation
        camera.setDisplayOrientation(displayRotation)
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

        val previewResolution = camera.getPreviewResolution(imageRotation)

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
        (cachedZoomParameters ?: camera.parameters)
                .apply {
                    zoom = (maxZoom * level).toInt()
                }
                .let {
                    cachedZoomParameters = it
                    camera.parameters = it
                }
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
                displayOrientationDegrees = displayRotation,
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
            with(capabilities) {
                meteringAreas = null
                focusAreas = null
            }
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

private fun computeImageOrientation(
        degrees: Int,
        characteristics: Characteristics
) = computeImageOrientation(
        screenRotationDegrees = degrees,
        cameraRotationDegrees = characteristics.orientation,
        cameraIsMirrored = characteristics.isMirrored
)

private fun computeDisplayOrientation(
        degrees: Int,
        characteristics: Characteristics
) = computeDisplayOrientation(
        screenRotationDegrees = degrees,
        cameraRotationDegrees = characteristics.orientation,
        cameraIsMirrored = characteristics.isMirrored
)

private fun Camera.updateParameters(newParameters: CameraParameters) {
    parameters = parameters.applyNewParameters(newParameters)
}

@Throws(IOException::class)
private fun Camera.setDisplaySurface(
        preview: Preview
): Surface = when (preview) {
    is Preview.Texture -> preview.surfaceTexture
            .also {
                setPreviewTexture(it)
            }
            .let {
                Surface(it)
            }
    is Preview.Surface -> preview.surfaceHolder
            .also {
                setPreviewDisplay(it)
            }
            .surface
}

private fun Camera.getPreviewResolution(imageRotation: Int): Resolution {
    val previewSize = parameters.previewSize

    val size = PreviewSize(
            previewSize.width,
            previewSize.height
    )

    return size.run {
        when (imageRotation) {
            0, 180 -> this
            else -> flipDimensions()
        }
    }
}

private fun PreviewStream.updateProcessorSafely(frameProcessor: ((Frame) -> Unit)?) {
    clearProcessors()
    when (frameProcessor) {
        null -> stop()
        else -> {
            addProcessor(frameProcessor)
            start()
        }
    }
}

private fun Capabilities.canSetFocusingAreas(): Boolean {
    return maxMeteringAreas > 0 || maxFocusAreas > 0
}
