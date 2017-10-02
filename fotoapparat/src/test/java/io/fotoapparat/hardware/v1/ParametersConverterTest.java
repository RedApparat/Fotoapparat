package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;

import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("deprecation")
public class ParametersConverterTest {

    @Mock
    CameraParametersDecorator outputParameters;

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
        CameraParametersDecorator result = testee.toPlatformParameters(
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
        testee.toPlatformParameters(
                input,
                outputParameters
        );

        // Then
        verify(outputParameters).setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    @Test
    public void setFlash() throws Exception {
        // Given
        Parameters input = new Parameters();
        input.putValue(
                Parameters.Type.FLASH,
                Flash.TORCH
        );

        // When
        testee.toPlatformParameters(
                input,
                outputParameters
        );

        // Then
        verify(outputParameters).setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
    }

    @Test
    public void setPictureSize() throws Exception {
        // Given
        Parameters input = new Parameters();
        input.putValue(
                Parameters.Type.PICTURE_SIZE,
                new Size(10, 20)
        );

        // When
        testee.toPlatformParameters(
                input,
                outputParameters
        );

        // Then
        verify(outputParameters).setPictureSize(10, 20);
    }

    @Test
    public void setPreviewSize() throws Exception {
        // Given
        Parameters input = new Parameters();
        input.putValue(
                Parameters.Type.PREVIEW_SIZE,
                new Size(10, 20)
        );

        // When
        testee.toPlatformParameters(
                input,
                outputParameters
        );

        // Then
        verify(outputParameters).setPreviewSize(10, 20);
    }

}