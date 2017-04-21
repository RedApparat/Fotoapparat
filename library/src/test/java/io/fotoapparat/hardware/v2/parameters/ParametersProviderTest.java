package io.fotoapparat.hardware.v2.parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ParametersProviderTest {

    @Mock
    Parameters parameters;

    @InjectMocks
    ParametersProvider testee;

    @Test
    public void getFlash() throws Exception {
        // Given
        given(parameters.getValue(Parameters.Type.FLASH))
                .willReturn(Flash.OFF);

        testee.updateParameters(parameters);

        // When
        Flash flash = testee.getFlash();

        // Then
        assertEquals(Flash.OFF, flash);
    }

    @Test
    public void getFocus() throws Exception {
        // Given
        given(parameters.getValue(Parameters.Type.FOCUS_MODE))
                .willReturn(FocusMode.CONTINUOUS_FOCUS);

        testee.updateParameters(parameters);

        // When
        FocusMode focusMode = testee.getFocus();

        // Then
        assertEquals(FocusMode.CONTINUOUS_FOCUS, focusMode);
    }

    @Test
    public void getCaptureSize() throws Exception {
        // Given
        given(parameters.getValue(Parameters.Type.PICTURE_SIZE))
                .willReturn(new Size(4000, 3000));

        testee.updateParameters(parameters);

        // When
        Size captureSize = testee.getStillCaptureSize();
        float stillCaptureAspectRatio = testee.getStillCaptureAspectRatio();

        // Then
        assertEquals(new Size(4000, 3000), captureSize);
        assertEquals(4000f / 3000, stillCaptureAspectRatio);
    }

    @Test
    public void getPreviewSize() throws Exception {
        // Given
        given(parameters.getValue(Parameters.Type.PREVIEW_SIZE))
                .willReturn(new Size(1920, 1080));

        testee.updateParameters(parameters);

        // When
        Size previewSize = testee.getPreviewSize();

        // Then
        assertEquals(new Size(1920, 1080), previewSize);
    }
}