package io.fotoapparat.routine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;

import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class StopCameraRoutineTest {

    @Mock
    CameraDevice cameraDevice;

    @InjectMocks
    StopCameraRoutine testee;

    @Test
    public void stop() throws Exception {
        // When
        testee.run();

        // Then
        InOrder inOrder = inOrder(cameraDevice);

        inOrder.verify(cameraDevice).stopPreview();
        inOrder.verify(cameraDevice).close();
    }
}