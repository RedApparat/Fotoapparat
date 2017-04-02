package io.fotoapparat.routine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.DisplayOrientationSensor;
import io.fotoapparat.test.ImmediateExecutor;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateDisplayOrientationListenerRoutineTest {

	@Mock
	CameraDevice cameraDevice;
	@Mock
	DisplayOrientationSensor displayOrientationSensor;

	UpdateOrientationRoutine testee;

	@Before
	public void setUp() throws Exception {
		testee = new UpdateOrientationRoutine(
				cameraDevice,
				displayOrientationSensor,
				new ImmediateExecutor()
		);
	}

	@Test
	public void start() throws Exception {
		// When
		testee.start();

		// Then
		verify(displayOrientationSensor).start(testee);
	}

	@Test
	public void stop() throws Exception {
		// When
		testee.stop();

		// Then
		verify(displayOrientationSensor).stop();
	}

	@Test
	public void onOrientationChanged() throws Exception {
		// When
		testee.onOrientationChanged(90);

		// Then
		verify(cameraDevice).setDisplayOrientation(90);
	}

}