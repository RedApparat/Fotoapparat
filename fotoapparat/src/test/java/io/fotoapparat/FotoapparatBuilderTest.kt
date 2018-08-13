package io.fotoapparat

import android.content.Context
import android.view.WindowManager
import io.fotoapparat.characteristic.LensPosition
import io.fotoapparat.error.CameraErrorCallback
import io.fotoapparat.log.Logger
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.*
import io.fotoapparat.util.FrameProcessor
import io.fotoapparat.view.CameraRenderer
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FotoapparatBuilderTest {

    @Mock
    lateinit var context: Context
    @Mock
    lateinit var cameraRenderer: CameraRenderer

    @Mock
    lateinit var photoResolutionSelector: Iterable<Resolution>.() -> Resolution
    @Mock
    lateinit var previewResolutionSelector: ResolutionSelector
    @Mock
    lateinit var lensPositionSelector: Iterable<LensPosition>.() -> LensPosition
    @Mock
    lateinit var focusModeSelector: FocusModeSelector
    @Mock
    lateinit var flashSelector: FlashSelector
    @Mock
    lateinit var previewFpsRangeSelector: FpsRangeSelector
    @Mock
    lateinit var sensorSensitivitySelector: SensorSensitivitySelector
    @Mock
    lateinit var jpegQualitySelector: QualitySelector
    @Mock
    lateinit var frameProcessor: FrameProcessor

    @Mock
    lateinit var logger: Logger

    @Mock
    lateinit var cameraErrorCallback: CameraErrorCallback

    @Before
    fun setUp() {
        given(context.getSystemService(Context.WINDOW_SERVICE))
                .willReturn(Mockito.mock(WindowManager::class.java))
    }

    @Test
    fun `Only mandatory parameters`() {
        // Given
        val builder = builderWithMandatoryArguments()

        // When
        val result = builder.build()

        // Then
        assertNotNull(result)
    }

    @Test(expected = IllegalStateException::class)
    fun `Forgotten view mandatory parameter`() {
        // Given

        // When
        FotoapparatBuilder(context).build()

        // Then
        // throws exception
    }

    @Test
    fun `lens position has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.lensPositionSelector)
    }

    @Test
    fun `lens position is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .lensPosition(lensPositionSelector)

        // Then
        assertEquals(
                lensPositionSelector,
                builder.lensPositionSelector
        )
    }

    @Test
    fun `logger has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.logger)
    }

    @Test
    fun `logger is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .logger(logger)

        // Then
        assertEquals(
                logger,
                builder.logger
        )
    }

    @Test
    fun `focusMode has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.configuration.focusMode)
    }

    @Test
    fun `focusMode is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .focusMode(focusModeSelector)

        // Then
        assertEquals(
                focusModeSelector,
                builder.configuration.focusMode
        )
    }

    @Test
    fun `previewFpsRange has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.configuration.previewFpsRange)
    }

    @Test
    fun `previewFpsRange is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .previewFpsRange(previewFpsRangeSelector)

        // Then
        assertEquals(
                previewFpsRangeSelector,
                builder.configuration.previewFpsRange
        )
    }

    @Test
    fun `flashMode has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.configuration.flashMode)
    }

    @Test
    fun `flashMode is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .flash(flashSelector)

        // Then
        assertEquals(
                flashSelector,
                builder.configuration.flashMode
        )
    }


    @Test
    fun `sensorSensitivity has not default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNull(builder.configuration.sensorSensitivity)
    }

    @Test
    fun `sensorSensitivity is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .sensorSensitivity(sensorSensitivitySelector)

        // Then
        assertEquals(
                sensorSensitivitySelector,
                builder.configuration.sensorSensitivity
        )
    }

    @Test
    fun `jpegQuality has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.configuration.jpegQuality)
    }

    @Test
    fun `jpegQuality is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .jpegQuality(jpegQualitySelector)

        // Then
        assertEquals(
                jpegQualitySelector,
                builder.configuration.jpegQuality
        )
    }

    @Test
    fun `frameProcessor null by default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNull(builder.configuration.frameProcessor)
    }

    @Test
    fun `frameProcessor is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .frameProcessor(frameProcessor)

        // Then
        assertEquals(
                frameProcessor,
                builder.configuration.frameProcessor
        )
    }

    @Test
    fun `photoResolution is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .photoResolution(photoResolutionSelector)

        // Then
        assertEquals(
                photoResolutionSelector,
                builder.configuration.pictureResolution
        )
    }

    @Test
    fun `previewResolution has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.configuration.previewResolution)
    }

    @Test
    fun `previewResolution is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .previewResolution(previewResolutionSelector)

        // Then
        assertEquals(
                previewResolutionSelector,
                builder.configuration.previewResolution
        )
    }

    @Test
    fun `previewStyle has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.scaleType)
    }

    @Test
    fun `previewStyle is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .previewScaleType(ScaleType.CenterInside)

        // Then
        assertEquals(
                ScaleType.CenterInside,
                builder.scaleType
        )
    }

    @Test
    fun `cameraErrorCallback has default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNotNull(builder.cameraErrorCallback)
    }

    @Test
    fun `cameraErrorCallback is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .cameraErrorCallback(cameraErrorCallback)

        // Then
        assertEquals(
                cameraErrorCallback,
                builder.cameraErrorCallback
        )
    }

    private fun builderWithMandatoryArguments(): FotoapparatBuilder {
        return FotoapparatBuilder(context)
                .into(cameraRenderer)
    }
}