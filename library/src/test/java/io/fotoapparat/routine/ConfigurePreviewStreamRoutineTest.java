package io.fotoapparat.routine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.preview.PreviewStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurePreviewStreamRoutineTest {

    @Mock
    CameraDevice cameraDevice;
    @Mock
    PreviewStream previewStream;
    @Mock
    FrameProcessor frameProcessor;

    @InjectMocks
    ConfigurePreviewStreamRoutine testee;

    @Before
    public void setUp() throws Exception {
        given(cameraDevice.getPreviewStream())
                .willReturn(previewStream);
    }

    @Test
    public void configurePreview() throws Exception {
        // When
        testee.run();

        // Then
        InOrder inOrder = inOrder(previewStream);

        inOrder.verify(previewStream).addProcessor(frameProcessor);
        inOrder.verify(previewStream).start();
    }

    @Test
    public void noFrameProcessor() throws Exception {
        // Given
        ConfigurePreviewStreamRoutine testee = new ConfigurePreviewStreamRoutine(
                cameraDevice,
                null
        );

        // When
        testee.run();

        // Then
        verifyZeroInteractions(previewStream);
    }

}