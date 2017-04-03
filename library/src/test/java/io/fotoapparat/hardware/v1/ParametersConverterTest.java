package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.Parameters;
import io.fotoapparat.parameter.FocusMode;

import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("deprecation")
public class ParametersConverterTest {

    @Mock
    Camera.Parameters outputParameters;

    ParametersConverter testee;

    @Before
    public void setUp() throws Exception {
        testee = new ParametersConverter();
    }

    @Test
    public void returnSameValue() throws Exception {
        // Given
        Parameters input = new Parameters();

        // When
        Camera.Parameters result = testee.convert(
                input,
                outputParameters
        );

        // Then
        assertSame(
                result,
                outputParameters
        );
    }

    @Test
    public void setFocusMode() throws Exception {
        // Given
        Parameters input = new Parameters();
        input.putValue(
                Parameters.Type.FOCUS_MODE,
                FocusMode.AUTO
        );

        // When
        testee.convert(
                input,
                outputParameters
        );

        // Then
        verify(outputParameters).setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

}