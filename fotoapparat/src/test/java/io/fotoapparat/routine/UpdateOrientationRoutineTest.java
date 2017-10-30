package io.fotoapparat.routine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.OrientationSensor;
import io.fotoapparat.log.Logger;
import io.fotoapparat.test.ImmediateExecutor;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UpdateOrientationRoutineTest {

    @Mock
    CameraDevice cameraDevice;
    @Mock
    OrientationSensor orientationSensor;
    @Mock
    Logger logger;

    UpdateOrientationRoutine testee;

    @Before
    public void setUp() throws Exception {
        testee = new UpdateOrientationRoutine(
                cameraDevice,
                orientationSensor,
                new ImmediateExecutor(),
                logger
        );
    }

    @Test
    public void start() throws Exception {
        // When
        testee.start();

        // Then
        verify(orientationSensor).start(testee);
        verifyZeroInteractions(logger);
    }

    @Test
    public void stop() throws Exception {
        // When
        testee.stop();

        // Then
        verify(orientationSensor).stop();
        verifyZeroInteractions(logger);
    }

    @Test
    public void onOrientationChanged() throws Exception {
        // When
        testee.onOrientationChanged(90);

        // Then
        verify(cameraDevice).setDisplayOrientation(90);
        verifyZeroInteractions(logger);
    }

    @Test
    public void onOrientationChangedFailed() throws Exception {
        // Given
        RuntimeException exception = new RuntimeException("test");
        doThrow(exception)
                .when(cameraDevice)
                .setDisplayOrientation(anyInt());

        // When
        testee.onOrientationChanged(90);

        // Then
        verify(cameraDevice).setDisplayOrientation(90);
        verify(logger).log(anyString());
    }

}
