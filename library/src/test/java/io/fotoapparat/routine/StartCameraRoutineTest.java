package io.fotoapparat.routine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.orientation.ScreenOrientationProvider;
import io.fotoapparat.hardware.provider.AvailableLensPositionsProvider;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.provider.InitialParametersProvider;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class StartCameraRoutineTest {

	static final int SCREEN_ROTATION_DEGREES = 90;
	static final Parameters INITIAL_PARAMETERS = new Parameters();

	@Mock
	AvailableLensPositionsProvider availableLensPositionsProvider;
	@Mock
	CameraDevice cameraDevice;
	@Mock
	CameraRenderer cameraRenderer;
	@Mock
	SelectorFunction<LensPosition> lensPositionSelector;
	@Mock
	ScreenOrientationProvider screenOrientationProvider;
	@Mock
	InitialParametersProvider initialParametersProvider;

	@InjectMocks
	StartCameraRoutine testee;

	@Test
	public void routine() throws Exception {
		// Given
		List<LensPosition> availableLensPositions = asList(
				LensPosition.FRONT,
				LensPosition.BACK
		);

		LensPosition preferredLensPosition = LensPosition.FRONT;

		givenLensPositionsAvailable(availableLensPositions);
		givenPositionSelected(preferredLensPosition);
		givenScreenRotation();
		givenInitialParametersAvailable();

		// When
		testee.run();

		// Then
		InOrder inOrder = inOrder(
				cameraDevice,
				cameraRenderer,
				lensPositionSelector
		);

		inOrder.verify(lensPositionSelector).select(availableLensPositions);
		inOrder.verify(cameraDevice).open(preferredLensPosition);
		inOrder.verify(cameraDevice).updateParameters(INITIAL_PARAMETERS);
		inOrder.verify(cameraDevice).setDisplayOrientation(SCREEN_ROTATION_DEGREES);
		inOrder.verify(cameraRenderer).attachCamera(cameraDevice);
		inOrder.verify(cameraDevice).startPreview();
	}

	private void givenInitialParametersAvailable() {
		given(initialParametersProvider.initialParameters())
				.willReturn(INITIAL_PARAMETERS);
	}

	private void givenScreenRotation() {
		given(screenOrientationProvider.getScreenRotation())
				.willReturn(SCREEN_ROTATION_DEGREES);
	}

	private void givenPositionSelected(LensPosition lensPosition) {
		given(lensPositionSelector.select(ArgumentMatchers.<LensPosition>anyCollection()))
				.willReturn(lensPosition);
	}

	private void givenLensPositionsAvailable(List<LensPosition> lensPositions) {
		given(availableLensPositionsProvider.getAvailableLensPositions())
				.willReturn(lensPositions);
	}

}