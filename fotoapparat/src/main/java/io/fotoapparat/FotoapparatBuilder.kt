package io.fotoapparat

import android.content.Context
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.error.CameraErrorCallback
import io.fotoapparat.error.CameraErrorListener
import io.fotoapparat.log.Logger
import io.fotoapparat.log.none
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.preview.Frame
import io.fotoapparat.selector.*
import io.fotoapparat.util.FrameProcessor
import io.fotoapparat.view.CameraRenderer
import io.fotoapparat.view.CameraView
import io.fotoapparat.view.FocusView
import io.fotoapparat.preview.FrameProcessor as FrameProcessorJava

/**
 * Builder for [Fotoapparat].
 */
class FotoapparatBuilder internal constructor(private var context: Context) {

    internal var lensPositionSelector: LensPositionSelector = firstAvailable(
            back(),
            front(),
            external()
    )
    internal var cameraErrorCallback: CameraErrorCallback = {}
    internal var renderer: CameraRenderer? = null
    internal var focusView: FocusView? = null
    internal var scaleType: ScaleType = ScaleType.CenterCrop
    internal var logger: Logger = none()

    internal var configuration = CameraConfiguration.default()

    /**
     * @param selector camera sensor position from list of available positions.
     */
    fun lensPosition(selector: LensPositionSelector): FotoapparatBuilder =
            apply { lensPositionSelector = selector }

    /**
     * @param scaleType of preview inside the view.
     */
    fun previewScaleType(scaleType: ScaleType): FotoapparatBuilder =
            apply { this.scaleType = scaleType }

    /**
     * @param selector selects resolution of the photo (in pixels) from list of available resolutions.
     */
    fun photoResolution(selector: ResolutionSelector): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                pictureResolution = selector
        )
    }

    /**
     * @param selector selects size of preview stream (in pixels) from list of available resolutions.
     */
    fun previewResolution(selector: ResolutionSelector): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                previewResolution = selector
        )
    }


    /**
     * @param selector selects focus mode from list of available modes.
     */
    fun focusMode(selector: FocusModeSelector): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                focusMode = selector
        )
    }

    /**
     * @param selector selects flash mode from list of available modes.
     */
    fun flash(selector: FlashSelector): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                flashMode = selector
        )
    }

    /**
     * @param selector selects preview FPS range from list of available ranges.
     */
    fun previewFpsRange(selector: FpsRangeSelector): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                previewFpsRange = selector
        )
    }

    /**
     * @param selector selects ISO value from range of available values.
     */
    fun sensorSensitivity(selector: SensorSensitivitySelector): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                sensorSensitivity = selector
        )
    }

    /**
     * @param selector of the Jpeg picture quality.
     */
    fun jpegQuality(selector: QualitySelector): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                jpegQuality = selector
        )
    }

    /**
     * @param selector selects exposure compensation value from available range.
     */
    fun exposureCompensation(selector: ExposureSelector): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                exposureCompensation = selector
        )
    }

    /**
     * @param frameProcessor receives preview frames for processing.
     * @see FrameProcessorJava
     */
    fun frameProcessor(frameProcessor: FrameProcessor): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                frameProcessor = frameProcessor
        )
    }

    /**
     * @param frameProcessor receives preview frames for processing.
     * @see FrameProcessorJava
     */
    fun frameProcessor(frameProcessor: FrameProcessorJava?): FotoapparatBuilder = apply {
        configuration = configuration.copy(
                frameProcessor = frameProcessor?.let { it::process }
        )
    }

    /**
     * @param logger logger which will print logs. No logger is set by default.
     * @see io.fotoapparat.log.Loggers
     */
    fun logger(logger: Logger): FotoapparatBuilder =
            apply { this.logger = logger }

    /**
     * @param callback which will be notified when camera error happens in Fotoapparat.
     * @see CameraErrorListener
     */
    fun cameraErrorCallback(callback: CameraErrorListener): FotoapparatBuilder =
            apply { cameraErrorCallback = { callback.onError(it) } }

    /**
     * @param callback which will be notified when camera error happens in Fotoapparat.
     * @see CameraErrorListener
     */
    fun cameraErrorCallback(callback: CameraErrorCallback): FotoapparatBuilder =
            apply { cameraErrorCallback = callback }

    /**
     * @param renderer view which will draw the stream from the camera.
     * @see CameraView
     */
    fun into(renderer: CameraRenderer): FotoapparatBuilder =
            apply { this.renderer = renderer }

    /**
     * @param focusView view which will be used for touch to focus.
     * @see FocusView
     */
    fun focusView(focusView: FocusView): FotoapparatBuilder =
            apply { this.focusView = focusView }

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
                focusView = focusView,
                lensPosition = lensPositionSelector,
                cameraConfiguration = configuration,
                scaleType = scaleType,
                cameraErrorCallback = cameraErrorCallback,
                logger = logger
        )
    }

}