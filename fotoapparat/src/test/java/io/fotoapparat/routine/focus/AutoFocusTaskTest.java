package io.fotoapparat.routine.focus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.lens.FocusResult;

import static io.fotoapparat.result.FocusResult.FOCUSED;
import static io.fotoapparat.result.FocusResult.UNABLE_TO_FOCUS;
import static io.fotoapparat.test.TestUtils.resultOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AutoFocusTaskTest {

    @Mock
    CameraDevice cameraDevice;

    @InjectMocks
    AutoFocusTask testee;

    @Test
    public void autoFocus_Focused() throws Exception {
        // Given
        given(cameraDevice.autoFocus())
                .willReturn(new FocusResult(true, false));

        // When
        io.fotoapparat.result.FocusResult result = resultOf(testee);

        // Then
        assertEquals(result, FOCUSED);
    }

    @Test
    public void autoFocus_UnableToFocus() throws Exception {
        // Given
        given(cameraDevice.autoFocus())
                .willReturn(new FocusResult(false, false));

        // When
        io.fotoapparat.result.FocusResult result = resultOf(testee);

        // Then
        assertEquals(result, UNABLE_TO_FOCUS);
    }

}