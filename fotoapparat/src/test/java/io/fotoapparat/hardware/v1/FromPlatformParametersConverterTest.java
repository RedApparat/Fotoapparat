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

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("deprecation")
public class FromPlatformParametersConverterTest {

    @Mock
    CameraParametersDecorator platformParameters;

    @Mock
    Camera.Size pictureSize;

    @Mock
    Camera.Size previewSize;

    ParametersConverter testee;

    @Before
    public void setUp() throws Exception {
        testee = new ParametersConverter();
        pictureSize.width = 640;
        pictureSize.height = 480;

        previewSize.width = 320;
        previewSize.height = 240;

        // Given
        when(platformParameters.getPictureSize()).thenReturn(pictureSize);
        when(platformParameters.getPreviewSize()).thenReturn(previewSize);
        when(platformParameters.getFlashMode()).thenReturn(Camera.Parameters.FLASH_MODE_AUTO);
        when(platformParameters.getFocusMode()).thenReturn(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    @Test
    public void focusMode() throws Exception {
        // When
        Parameters parameters = testee.fromPlatformParameters(platformParameters);

        // Then
        assertEquals(FocusMode.AUTO, parameters.getValue(Parameters.Type.FOCUS_MODE));
    }

    @Test
    public void flash() throws Exception {
        // When
        Parameters parameters = testee.fromPlatformParameters(platformParameters);

        // Then
        assertEquals(Flash.AUTO, parameters.getValue(Parameters.Type.FLASH));
    }

    @Test
    public void pictureSize() throws Exception {
        // When
        Parameters parameters = testee.fromPlatformParameters(platformParameters);

        // Then
        Size value = parameters.getValue(Parameters.Type.PICTURE_SIZE);
        assertEquals(pictureSize.width, value.width);
        assertEquals(pictureSize.height, value.height);
    }

    @Test
    public void previewSize() throws Exception {
        // When
        Parameters parameters = testee.fromPlatformParameters(platformParameters);

        // Then
        Size value = parameters.getValue(Parameters.Type.PREVIEW_SIZE);
        assertEquals(previewSize.width, value.width);
        assertEquals(previewSize.height, value.height);
    }

}