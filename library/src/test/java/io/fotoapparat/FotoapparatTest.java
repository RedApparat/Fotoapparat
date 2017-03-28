package io.fotoapparat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.test.ImmediateExecutor;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatTest {

	@Mock
	StartCameraRoutine startCameraRoutine;

	Fotoapparat testee;

	@Before
	public void setUp() throws Exception {
		testee = new Fotoapparat(
				startCameraRoutine,
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

}