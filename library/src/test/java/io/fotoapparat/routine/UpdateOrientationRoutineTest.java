package io.fotoapparat.routine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.OrientationSensor;
import io.fotoapparat.test.ImmediateExecutor;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateOrientationRoutineTest {

    @Mock
    CameraDevice cameraDevice;
    @Mock
    OrientationSensor orientationSensor;

    UpdateOrientationRoutine testee;

    @Before
    public void setUp() throws Exception {
        testee = new UpdateOrientationRoutine(
                cameraDevice,
                orientationSensor,
                new ImmediateExecutor()
        );
    }

    @Test
    public void start() throws Exception {
        // When
        testee.start();

        // Then
        verify(orientationSensor).start(testee);
    }

    @Test
    public void stop() throws Exception {
        // When
        testee.stop();

        // Then
        verify(orientationSensor).stop();
    }

    @Test
    public void onOrientationChanged() throws Exception {
        // When
        testee.onOrientationChanged(90);

        // Then
        verify(cameraDevice).setDisplayOrientation(90);
    }
}