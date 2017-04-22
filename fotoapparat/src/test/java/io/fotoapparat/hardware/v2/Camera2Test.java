package io.fotoapparat.hardware.v2;

import android.view.TextureView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.operators.ConnectionOperator;
import io.fotoapparat.hardware.operators.OrientationOperator;
import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.operators.SurfaceOperator;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.photo.Photo;
import io.fotoapparat.preview.PreviewStream;

import static java.util.Collections.singleton;
import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class Camera2Test {

    @Mock
    Logger logger;
    @Mock
    ConnectionOperator connectionOperator;
    @Mock
    PreviewOperator previewOperator;
    @Mock
    SurfaceOperator surfaceOperator;
    @Mock
    OrientationOperator orientationOperator;
    @Mock
    ParametersOperator parametersOperator;
    @Mock
    CapabilitiesOperator capabilitiesOperator;
    @Mock
    CaptureOperator captureOperator;
    @Mock
    PreviewStream previewStream;

    @InjectMocks
    Camera2 testee;

    @Test
    public void open() throws Exception {
        // When
        testee.open(LensPosition.FRONT);

        // Then
        verify(logger).log(anyString());
        verify(connectionOperator).open(LensPosition.FRONT);
    }

    @Test
    public void close() throws Exception {
        // When
        testee.close();

        // Then
        verify(logger).log(anyString());
        verify(connectionOperator).close();
    }

    @Test
    public void startPreview() throws Exception {
        // When
        testee.startPreview();

        // Then
        verify(logger).log(anyString());
        verify(previewOperator).startPreview();
    }

    @Test
    public void stopPreview() throws Exception {
        // When
        testee.stopPreview();

        // Then
        verify(logger).log(anyString());
        verify(previewOperator).stopPreview();
    }

    @Test
    public void setDisplaySurface() throws Exception {
        // When
        TextureView textureView = Mockito.mock(TextureView.class);
        testee.setDisplaySurface(textureView);

        // Then
        verify(logger).log(anyString());
        verify(surfaceOperator).setDisplaySurface(textureView);
    }

    @Test
    public void setDisplayOrientation() throws Exception {
        // When
        testee.setDisplayOrientation(90);

        // Then
        verify(logger).log(anyString());
        verify(orientationOperator).setDisplayOrientation(90);
    }

    @Test
    public void updateParameters() throws Exception {
        // When
        Parameters parameters = new Parameters();
        testee.updateParameters(parameters);

        // Then
        verify(logger).log(anyString());
        verify(parametersOperator).updateParameters(parameters);
    }

    @Test
    public void getCapabilities() throws Exception {
        // Given
        Capabilities capabilities = new Capabilities(
                Collections.<Size>emptySet(),
                Collections.<Size>emptySet(),
                singleton(FocusMode.MACRO),
                Collections.<Flash>emptySet()
        );
        given(capabilitiesOperator.getCapabilities())
                .willReturn(capabilities);

        // When
        Capabilities returnedCapabilities = testee.getCapabilities();

        // Then
        verify(logger).log(anyString());
        assertEquals(capabilities, returnedCapabilities);
    }

    @Test
    public void takePicture() throws Exception {
        // Given
        Photo photo = new Photo(new byte[0], 0);
        given(captureOperator.takePicture())
                .willReturn(photo);

        // When
        Photo returnedPhoto = testee.takePicture();

        // Then
        verify(logger).log(anyString());
        assertEquals(photo, returnedPhoto);
    }

    @Test
    public void previewStream() throws Exception {
        // Given

        // When
        PreviewStream previewStream = testee.getPreviewStream();

        // Then
        verify(logger).log(anyString());
        assertEquals(this.previewStream, previewStream);
    }
}