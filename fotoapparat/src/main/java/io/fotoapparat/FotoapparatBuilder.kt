package io.fotoapparat

import android.content.Context
import android.support.annotation.IntRange
import io.fotoapparat.characteristic.LensPosition
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.configuration.default
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.log.Logger
import io.fotoapparat.log.none
import io.fotoapparat.parameter.*
import io.fotoapparat.preview.Frame
import io.fotoapparat.view.CameraRenderer
import io.fotoapparat.view.CameraView

/**
 * Builder for [Fotoapparat].
 */
class FotoapparatBuilder internal constructor(private var context: Context) {

    internal var lensPositionSelector: (Iterable<LensPosition>.() -> LensPosition?)? = null
    internal var cameraErrorCallback: ((CameraException) -> Unit)? = null
    internal var frameProcessor: ((Frame) -> Unit?)? = null
    internal var renderer: CameraRenderer? = null
    internal var scaleType: ScaleType? = null
    internal var logger: Logger? = null

    internal var configuration = default()

    fun cameraConfiguration(cameraConfiguration: CameraConfiguration): FotoapparatBuilder {
        return this
    }

    /**
     * @param selector camera sensor position from list of available positions.
     */
    fun lensPosition(selector: Iterable<LensPosition>.() -> LensPosition?): FotoapparatBuilder {
        lensPositionSelector = selector
        return this
    }

    /**
     * @param scaleType of preview inside the view.
     */
    fun previewScaleType(scaleType: ScaleType): FotoapparatBuilder {
        this.scaleType = scaleType
        return this
    }

    /**
     * @param selector selects resolution of the photo (in pixels) from list of available resolutions.
     */
    fun photoResolution(selector: Iterable<Resolution>.() -> Resolution?): FotoapparatBuilder {
        configuration = configuration.copy(
                pictureResolution = selector
        )
        return this
    }

    /**
     * @param selector selects size of preview stream (in pixels) from list of available resolutions.
     */
    fun previewResolution(selector: Iterable<Resolution>.() -> Resolution?): FotoapparatBuilder {
        configuration = configuration.copy(
                previewResolution = selector
        )
        return this
    }


    /**
     * @param selector selects focus mode from list of available modes.
     */
    fun focusMode(selector: Iterable<FocusMode>.() -> FocusMode?): FotoapparatBuilder {
        configuration = configuration.copy(
                focusMode = selector
        )
        return this
    }

    /**
     * @param selector selects flash mode from list of available modes.
     */
    fun flash(selector: Iterable<Flash>.() -> Flash?): FotoapparatBuilder {
        configuration = configuration.copy(
                flashMode = selector
        )
        return this
    }

    /**
     * @param selector selects preview FPS range from list of available ranges.
     */
    fun previewFpsRange(selector: Iterable<FpsRange>.() -> FpsRange?): FotoapparatBuilder {
        configuration = configuration.copy(
                previewFpsRange = selector
        )
        return this
    }

    /**
     * @param selector selects ISO value from range of available values.
     */
    fun sensorSensitivity(selector: Iterable<Int>.() -> Int?): FotoapparatBuilder {
        configuration = configuration.copy(
                sensorSensitivity = selector
        )
        return this
    }

    /**
     * @param jpegQuality of the picture (1-100)
     */
    fun jpegQuality(@IntRange(from = 0, to = 100) jpegQuality: Int): FotoapparatBuilder {
//        configuration = configuration.copy(
//                jpegQuality = jpegQuality
//        ) // TODO
        return this
    }

    /**
     * @param frameProcessor receives preview frames for processing.
     * @see FrameProcessor
     */
    fun frameProcessor(frameProcessor: (Frame) -> Unit): FotoapparatBuilder {
        this.frameProcessor = frameProcessor
        return this
    }

    /**
     * @param logger logger which will print logs. No logger is set by default.
     * @see io.fotoapparat.log.Loggers
     */
    fun logger(logger: Logger): FotoapparatBuilder {
        this.logger = logger
        return this
    }

    /**
     * @param callback which will be notified when camera error happens in Fotoapparat.
     * @see CameraErrorCallback
     */
    fun cameraErrorCallback(callback: (CameraException) -> Unit): FotoapparatBuilder {
        cameraErrorCallback = callback
        return this
    }

    /**
     * @param renderer view which will draw the stream from the camera.
     * @see CameraView
     */
    fun into(renderer: CameraRenderer): FotoapparatBuilder {
        this.renderer = renderer
        return this
    }

    /**
     * @return set up instance of [Fotoapparat].
     * @throws IllegalStateException if some mandatory parameters are not specified.
     */
    fun build() = buildInternal(
            renderer = renderer,
            lensPositionSelector = lensPositionSelector
    )

    private fun buildInternal(
            renderer: CameraRenderer?,
            lensPositionSelector: (Iterable<LensPosition>.() -> LensPosition?)?
    ): Fotoapparat {
        if (renderer == null) {
            throw IllegalStateException("CameraRenderer is mandatory.")
        }

        if (lensPositionSelector == null) {
            throw IllegalStateException("LensPosition selector is mandatory.")
        }

        return Fotoapparat(
                context = context,
                view = renderer,
                lensPosition = lensPositionSelector,
                cameraConfiguration = configuration,
                scaleType = scaleType ?: ScaleType.CenterCrop,
                cameraErrorCallback = cameraErrorCallback ?: {},
                logger = logger ?: none()
        )
    }

}
