package io.fotoapparat.routine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AutoFocusRoutineTest {

	@Mock
	CameraDevice cameraDevice;

	@InjectMocks
	AutoFocusRoutine testee;

	@Test
	public void autoFocus() throws Exception {
		// When
		testee.run();

		// Then
		verify(cameraDevice).autoFocus();
	}
}