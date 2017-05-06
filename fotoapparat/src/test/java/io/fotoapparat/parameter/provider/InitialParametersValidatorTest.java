package io.fotoapparat.parameter.provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;

import static io.fotoapparat.parameter.Parameters.Type.FLASH;
import static io.fotoapparat.parameter.Parameters.Type.FOCUS_MODE;
import static io.fotoapparat.parameter.Parameters.Type.PICTURE_SIZE;
import static io.fotoapparat.parameter.Parameters.Type.PREVIEW_SIZE;

@RunWith(MockitoJUnitRunner.class)
public class InitialParametersValidatorTest {

    private Parameters parameters = new Parameters();

    @InjectMocks
    InitialParametersValidator testee;

    @Before
    public void setUp() throws Exception {
        parameters.putValue(PICTURE_SIZE, new Size(4000, 3000));
        parameters.putValue(PREVIEW_SIZE, new Size(1920, 1080));
        parameters.putValue(FOCUS_MODE, FocusMode.AUTO);
        parameters.putValue(FLASH, Flash.OFF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPictureSize() throws Exception {
        // Given
        parameters.putValue(PICTURE_SIZE, null);

        // When
        testee.validate(parameters);

        // Then
        // exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPreviewSize() throws Exception {
        // Given
        parameters.putValue(PREVIEW_SIZE, null);

        // When
        testee.validate(parameters);

        // Then
        // exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void noFocusMode() throws Exception {
        // Given
        parameters.putValue(FOCUS_MODE, null);

        // When
        testee.validate(parameters);

        // Then
        // exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void noFlash() throws Exception {
        // Given
        parameters.putValue(FLASH, null);

        // When
        testee.validate(parameters);

        // Then
        // exception
    }

}