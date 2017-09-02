package io.fotoapparat;

import android.content.Context;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.error.CameraErrorCallback;
import java.util.Collection;

import io.fotoapparat.hardware.provider.CameraProvider;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.view.CameraRenderer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatBuilderTest {

    @Mock
    Context context;
    @Mock
    CameraProvider cameraProvider;
    @Mock
    CameraRenderer cameraRenderer;

    @Mock
    SelectorFunction<Collection<Size>, Size> photoSizeSelector;
    @Mock
    SelectorFunction<Collection<Size>, Size> previewSizeSelector;
    @Mock
    SelectorFunction<Collection<LensPosition>, LensPosition> lensPositionSelector;
    @Mock
    SelectorFunction<Collection<FocusMode>, FocusMode> focusModeSelector;
    @Mock
    SelectorFunction<Collection<Flash>, Flash> flashSelector;
    @Mock
    SelectorFunction<Collection<Range<Integer>>, Range<Integer>> previewFpsRangeSelector;

    @Mock
    FrameProcessor frameProcessor;

    @Mock
    Logger logger;

    @Mock
    CameraErrorCallback cameraErrorCallback;

    @Before
    public void setUp() throws Exception {
        given(context.getSystemService(Context.WINDOW_SERVICE))
                .willReturn(Mockito.mock(WindowManager.class));
    }

    @Test
    public void onlyMandatoryParameters() throws Exception {
        // Given
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // When
        Fotoapparat result = builder.build();

        // Then
        assertNotNull(result);
    }

    @Test
    public void cameraProvider_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.cameraProvider);
    }

    @Test
    public void cameraProvider_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .cameraProvider(cameraProvider);

        // Then
        assertEquals(
                cameraProvider,
                builder.cameraProvider
        );
    }

    @Test
    public void logger_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.logger);
    }

    @Test
    public void logger_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .logger(logger);

        // Then
        assertEquals(
                logger,
                builder.logger
        );
    }

    @Test
    public void focusMode_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.focusModeSelector);
    }

    @Test
    public void focusMode_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .focusMode(focusModeSelector);

        // Then
        assertEquals(
                focusModeSelector,
                builder.focusModeSelector
        );
    }

    @Test
    public void previewFpsRange_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.previewFpsRangeSelector);
    }

    @Test
    public void previewFpsRange_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .previewFpsRange(previewFpsRangeSelector);

        // Then
        assertEquals(
                previewFpsRangeSelector,
                builder.previewFpsRangeSelector
        );
    }

    @Test
    public void flashMode_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .flash(flashSelector);

        // Then
        assertEquals(
                flashSelector,
                builder.flashSelector
        );
    }

    @Test
    public void frameProcessor_NullByDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNull(builder.frameProcessor);
    }

    @Test
    public void frameProcessor_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .frameProcessor(frameProcessor);

        // Then
        assertEquals(
                frameProcessor,
                builder.frameProcessor
        );
    }

    @Test
    public void photoSize_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .photoSize(photoSizeSelector);

        // Then
        assertEquals(
                photoSizeSelector,
                builder.photoSizeSelector
        );
    }

    @Test
    public void previewSize_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.previewSizeSelector);
    }

    @Test
    public void previewSize_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .previewSize(previewSizeSelector);

        // Then
        assertEquals(
                previewSizeSelector,
                builder.previewSizeSelector
        );
    }

    @Test
    public void previewStyle_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.scaleType);
    }

    @Test
    public void previewStyle_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .previewScaleType(ScaleType.CENTER_INSIDE);

        // Then
        assertEquals(
                ScaleType.CENTER_INSIDE,
                builder.scaleType
        );
    }
    @Test
    public void cameraErrorCallback_HasDefault() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments();

        // Then
        assertNotNull(builder.cameraErrorCallback);
    }

    @Test
    public void cameraErrorCallback_IsConfigurable() throws Exception {
        // When
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .cameraErrorCallback(cameraErrorCallback);

        // Then
        assertEquals(
                cameraErrorCallback,
                builder.cameraErrorCallback
        );
    }

    @Test(expected = IllegalStateException.class)
    @SuppressWarnings("ConstantConditions")
    public void rendererIsMandatory() throws Exception {
        // Given
        FotoapparatBuilder builder = builderWithMandatoryArguments()
                .into(null);

        // When
        builder.build();

        // Then
        // Expect exception
    }

	@Test(expected = IllegalStateException.class)
	@SuppressWarnings("ConstantConditions")public void lensPositionIsMandatory() throws Exception {
		// Given
		FotoapparatBuilder builder = builderWithMandatoryArguments()
				.lensPosition(null);

        // When
        builder.build();

        // Then
        // Expect exception
    }

	@Test(expected = IllegalStateException.class)
	@SuppressWarnings("ConstantConditions")public void photoSizeIsMandatory() throws Exception {
		// Given
		FotoapparatBuilder builder = builderWithMandatoryArguments()
				.photoSize(null);

        // When
        builder.build();

        // Then
        // Expect exception
    }

    private FotoapparatBuilder builderWithMandatoryArguments() {
        return new FotoapparatBuilder(context)
                .lensPosition(lensPositionSelector)
                .photoSize(photoSizeSelector)
                .into(cameraRenderer);
    }
}