package io.fotoapparat.parameter.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.range.Ranges;
import io.fotoapparat.parameter.selector.PreviewFpsRangeSelectors;
import io.fotoapparat.parameter.selector.SensorSensitivitySelectors;
import io.fotoapparat.parameter.selector.SizeSelectors;

import static io.fotoapparat.parameter.selector.FlashSelectors.torch;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.test.TestUtils.asSet;
import static io.fotoapparat.util.TestSelectors.selectFromCollection;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InitialParametersProviderTest {

    static final Size PHOTO_SIZE = new Size(4000, 3000);
    static final Size PREVIEW_SIZE = new Size(2000, 1500);
    static final Size PREVIEW_SIZE_WRONG_ASPECT_RATIO = new Size(1000, 1000);
    static final Range<Integer> PREVIEW_FPS_RANGE = Ranges.continuousRange(30000, 30000);
    static final Integer SENSOR_SENSITIVITY = 1000;
    static final Range<Integer> SENSOR_SENSITIVITY_RANGE = Ranges.continuousRange(SENSOR_SENSITIVITY);

    static final Set<Size> ALL_PREVIEW_SIZES = asSet(
            PREVIEW_SIZE,
            PREVIEW_SIZE_WRONG_ASPECT_RATIO
    );
    static final Set<Range<Integer>> PREVIEW_FPS_RANGES = asSet(PREVIEW_FPS_RANGE);

    @Mock
    InitialParametersValidator initialParametersValidator;
    @Mock
    CapabilitiesOperator capabilitiesOperator;

    @Test
    public void validPreviewSizeSelector_WithValidAspectRatio() throws Exception {
        // When
        Size result = InitialParametersProvider
                .validPreviewSizeSelector(
                        PHOTO_SIZE,
                        selectFromCollection(PREVIEW_SIZE)
                )
                .select(ALL_PREVIEW_SIZES);

        // Then
        assertEquals(
                PREVIEW_SIZE,
                result
        );
    }

    @Test
    public void validPreviewSizeSelector_NoPreviewSizeWithSameAspectRatio() throws Exception {
        // Given
        Size photoSize = new Size(10000, 100);

        // When
        Size result = InitialParametersProvider
                .validPreviewSizeSelector(
                        photoSize,
                        selectFromCollection(PREVIEW_SIZE)
                )
                .select(ALL_PREVIEW_SIZES);

        // Then
        assertEquals(
                PREVIEW_SIZE,
                result
        );
    }

    @Test
    public void initialParameters() throws Exception {
        // Given
        given(capabilitiesOperator.getCapabilities())
                .willReturn(new Capabilities(
                        asSet(PHOTO_SIZE),
                        ALL_PREVIEW_SIZES,
                        asSet(FocusMode.AUTO),
                        asSet(Flash.TORCH),
                        PREVIEW_FPS_RANGES,
                        SENSOR_SENSITIVITY_RANGE,
                        true
                ));

        InitialParametersProvider testee = new InitialParametersProvider(
                capabilitiesOperator,
                SizeSelectors.biggestSize(),
                SizeSelectors.biggestSize(),
                autoFocus(),
                torch(),
                PreviewFpsRangeSelectors.rangeWithHighestFps(),
                SensorSensitivitySelectors.highestSensorSensitivity(),
                initialParametersValidator
        );

        // When
        Parameters parameters = testee.initialParameters();

        // Then
        assertEquals(
                new Parameters()
                        .putValue(
                                Parameters.Type.PICTURE_SIZE,
                                PHOTO_SIZE
                        )
                        .putValue(
                                Parameters.Type.PREVIEW_SIZE,
                                PREVIEW_SIZE
                        )
                        .putValue(
                                Parameters.Type.FOCUS_MODE,
                                FocusMode.AUTO
                        )
                        .putValue(
                                Parameters.Type.FLASH,
                                Flash.TORCH
                        )
                        .putValue(
                                Parameters.Type.PREVIEW_FPS_RANGE,
                                PREVIEW_FPS_RANGE
                        )
                        .putValue(
                                Parameters.Type.SENSOR_SENSITIVITY,
                                SENSOR_SENSITIVITY
                        ),
                parameters
        );

        verify(initialParametersValidator).validate(parameters);
    }

}