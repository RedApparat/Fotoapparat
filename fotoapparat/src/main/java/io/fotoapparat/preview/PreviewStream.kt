@file:Suppress("DEPRECATION")

package io.fotoapparat.preview

import android.graphics.ImageFormat
import android.hardware.Camera
import io.fotoapparat.hardware.frameProcessingExecutor
import io.fotoapparat.hardware.orientation.Orientation
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.util.FrameProcessor
import java.util.*

/**
 * Preview stream of Camera.
 */
internal class PreviewStream(private val camera: Camera) {

    private val frameProcessors = LinkedHashSet<FrameProcessor>()

    private var previewResolution: Resolution? = null

    /**
     * CW orientation.
     */
    var frameOrientation: Orientation = Orientation.Vertical.Portrait

    /**
     * Clears all processors.
     */
    private fun clearProcessors() {
        synchronized(frameProcessors) {
            frameProcessors.clear()
        }
    }

    /**
     * Registers new processor. If processor was already added before, does nothing.
     */
    private fun addProcessor(processor: FrameProcessor) {
        synchronized(frameProcessors) {
            frameProcessors.add(processor)
        }
    }

    /**
     * Starts preview stream. After preview is started frame processors will start receiving frames.
     */
    private fun start() {
        camera.addFrameToBuffer()

        camera.setPreviewCallbackWithBuffer { data, _ -> dispatchFrameOnBackgroundThread(data) }
    }

    /**
     * Stops preview stream.
     */
    private fun stop() {
        camera.setPreviewCallbackWithBuffer(null)
    }

    /**
     * Updates the frame processor safely.
     */
    fun updateProcessorSafely(frameProcessor: FrameProcessor?) {
        clearProcessors()
        if (frameProcessor == null) {
            stop()
        } else {
            addProcessor(frameProcessor)
            start()
        }
    }

    private fun Camera.addFrameToBuffer() {
        addCallbackBuffer(parameters.allocateBuffer())
    }

    private fun Camera.Parameters.allocateBuffer(): ByteArray {
        ensureNv21Format()

        previewResolution = Resolution(
                previewSize.width,
                previewSize.height
        )

        return ByteArray(previewSize.bytesPerFrame())
    }

    private fun dispatchFrameOnBackgroundThread(data: ByteArray) {
        frameProcessingExecutor.execute {
            synchronized(frameProcessors) {
                dispatchFrame(data)
            }
        }
    }

    private fun dispatchFrame(image: ByteArray) {
        val previewResolution = ensurePreviewSizeAvailable()

        val frame = Frame(
                size = previewResolution,
                image = image,
                rotation = frameOrientation.degrees
        )

        frameProcessors.forEach {
            it.invoke(frame)
        }

        returnFrameToBuffer(frame)
    }

    private fun ensurePreviewSizeAvailable(): Resolution =
            previewResolution
                    ?: throw IllegalStateException("previewSize is null. Frame was not added?")

    private fun returnFrameToBuffer(frame: Frame) {
        camera.addCallbackBuffer(
                frame.image
        )
    }

}

private fun Camera.Size.bytesPerFrame(): Int =
        width * height * ImageFormat.getBitsPerPixel(ImageFormat.NV21) / 8

private fun Camera.Parameters.ensureNv21Format() {
    if (previewFormat != ImageFormat.NV21) {
        throw UnsupportedOperationException("Only NV21 preview format is supported")
    }
}
