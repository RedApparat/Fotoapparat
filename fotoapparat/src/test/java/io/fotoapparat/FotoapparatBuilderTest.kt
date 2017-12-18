package io.fotoapparat

import android.content.Context
import android.view.WindowManager
import io.fotoapparat.characteristic.LensPosition
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.log.Logger
import io.fotoapparat.parameter.*
import io.fotoapparat.preview.Frame
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
    lateinit var previewResolutionSelector: Iterable<Resolution>.() -> Resolution?
    @Mock
    lateinit var lensPositionSelector: Iterable<LensPosition>.() -> LensPosition
    @Mock
    lateinit var focusModeSelector: Iterable<FocusMode>.() -> FocusMode?
    @Mock
    lateinit var flashSelector: Iterable<Flash>.() -> Flash?
    @Mock
    lateinit var previewFpsRangeSelector: Iterable<FpsRange>.() -> FpsRange?

    @Mock
    lateinit var frameProcessor: (Frame) -> Unit

    @Mock
    lateinit var logger: Logger

    @Mock
    lateinit var cameraErrorCallback: (CameraException) -> Unit

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
    fun `frameProcessor null by default`() {
        // When
        val builder = builderWithMandatoryArguments()

        // Then
        assertNull(builder.frameProcessor)
    }

    @Test
    fun `frameProcessor is configurable`() {
        // When
        val builder = builderWithMandatoryArguments()
                .frameProcessor(frameProcessor)

        // Then
        assertEquals(
                frameProcessor,
                builder.frameProcessor
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
                .lensPosition(lensPositionSelector)
                .photoResolution(photoResolutionSelector)
                .into(cameraRenderer)
    }
}