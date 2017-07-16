package io.fotoapparat.hardware.v2.parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.parameter.RendererParameters;
import io.fotoapparat.parameter.Size;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class RendererParametersProviderTest {

    @Mock
    ParametersProvider parametersProvider;
    @Mock
    OrientationManager orientationManager;
    @InjectMocks
    RendererParametersProvider testee;

    @Test
    public void getParameters() throws Exception {
        // Given
        Size size = new Size(1920, 1080);
        int sensorOrientation = 90;

        given(parametersProvider.getPreviewSize())
                .willReturn(size);
        given(orientationManager.getPhotoOrientation())
                .willReturn(sensorOrientation);

        // When
        RendererParameters rendererParameters = testee.getRendererParameters();

        // Then
        assertEquals(
                new RendererParameters(
                        size,
                        sensorOrientation
                ),
                rendererParameters
        );

    }
}