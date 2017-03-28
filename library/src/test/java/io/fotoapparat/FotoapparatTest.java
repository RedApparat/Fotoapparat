package io.fotoapparat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.routine.StopCameraRoutine;
import io.fotoapparat.routine.UpdateOrientationRoutine;
import io.fotoapparat.test.ImmediateExecutor;

import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatTest {

	@Mock
	StartCameraRoutine startCameraRoutine;
	@Mock
	StopCameraRoutine stopCameraRoutine;
	@Mock
	UpdateOrientationRoutine updateOrientationRoutine;

	Fotoapparat testee;

	@Before
	public void setUp() throws Exception {
		testee = new Fotoapparat(
				startCameraRoutine,
				stopCameraRoutine,
				updateOrientationRoutine,
				new ImmediateExecutor()
		);
	}

	@Test
	public void start() throws Exception {
		// When
		testee.start();

		// Then
		InOrder inOrder = inOrder(
				startCameraRoutine,
				updateOrientationRoutine
		);

		inOrder.verify(startCameraRoutine).run();
		inOrder.verify(updateOrientationRoutine).start();
	}

	@Test
	public void stop() throws Exception {
		// When
		testee.stop();

		// Then
		InOrder inOrder = inOrder(
				stopCameraRoutine,
				updateOrientationRoutine
		);

		inOrder.verify(updateOrientationRoutine).stop();
		inOrder.verify(stopCameraRoutine).run();
	}
}