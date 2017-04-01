package io.fotoapparat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.routine.StopCameraRoutine;
import io.fotoapparat.test.ImmediateExecutor;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatTest {

	@Mock
	StartCameraRoutine startCameraRoutine;
	@Mock
	StopCameraRoutine stopCameraRoutine;

	Fotoapparat testee;

	@Before
	public void setUp() throws Exception {
		testee = new Fotoapparat(
				startCameraRoutine,
				stopCameraRoutine,
				new ImmediateExecutor()
		);
	}

	@Test
	public void start() throws Exception {
		// When
		testee.start();

		// Then
		verify(startCameraRoutine).run();
	}

	@Test(expected = IllegalStateException.class)
	public void start_SecondTime() throws Exception {
		// Given
		testee.start();

		// When
		testee.start();

		// Then
		// Expect exception
	}

	@Test
	public void stop() throws Exception {
		// Given
		testee.start();

		// When
		testee.stop();

		// Then
		verify(stopCameraRoutine).run();
	}

	@Test(expected = IllegalStateException.class)
	public void stop_NotStarted() throws Exception {
		// When
		testee.stop();

		// Then
		// Expect exception
	}

}