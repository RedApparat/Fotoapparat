package io.fotoapparat

import android.content.Context
import io.fotoapparat.characteristic.LensPosition
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.log.Logger
import io.fotoapparat.log.none
import io.fotoapparat.parameter.*
import io.fotoapparat.preview.Frame
import io.fotoapparat.selector.back
import io.fotoapparat.selector.external
import io.fotoapparat.selector.firstAvailable
import io.fotoapparat.selector.front
import io.fotoapparat.view.CameraRenderer
import io.fotoapparat.view.CameraView

/**
 * Builder for [Fotoapparat].
 */
class FotoapparatBuilder internal constructor(private var context: Context) {

    internal var lensPositionSelector: Iterable<LensPosition>.() -> LensPosition? = firstAvailable(
            back(),
            front(),
            external()
    )
    internal var cameraErrorCallback: (CameraException) -> Unit = {}
    internal var renderer: CameraRenderer? = null
    internal var scaleType: ScaleType = ScaleType.CenterCrop
    internal var logger: Logger = none()

    internal var configuration = CameraConfiguration.default()

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
     * @param selector of the Jpeg picture quality.
     */
    fun jpegQuality(selector: IntRange.() -> Int?): FotoapparatBuilder {
        configuration = configuration.copy(
                jpegQuality = selector
        )
        return this
    }

    /**
     * @param frameProcessor receives preview frames for processing.
     * @see FrameProcessor
     */
    fun frameProcessor(frameProcessor: (Frame) -> Unit): FotoapparatBuilder {
        configuration = configuration.copy(
                frameProcessor = frameProcessor
        )
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
            renderer = renderer
    )

    private fun buildInternal(
            renderer: CameraRenderer?
    ): Fotoapparat {
        if (renderer == null) {
            throw IllegalStateException("CameraRenderer is mandatory.")
        }

        return Fotoapparat(
                context = context,
                view = renderer,
                lensPosition = lensPositionSelector,
                cameraConfiguration = configuration,
                scaleType = scaleType,
                cameraErrorCallback = cameraErrorCallback,
                logger = logger
        )
    }

}
