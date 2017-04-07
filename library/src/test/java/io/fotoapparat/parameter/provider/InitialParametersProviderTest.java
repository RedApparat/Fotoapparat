package io.fotoapparat.parameter.provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.selector.SelectorFunction;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class InitialParametersProviderTest {

	static final Set<FocusMode> FOCUS_MODES = asSet(FocusMode.FIXED);
	static final Set<Flash> FLASH = asSet(Flash.AUTO_RED_EYE);

	@Mock
	CameraDevice cameraDevice;
	@Mock
	SelectorFunction<FocusMode> focusModeSelector;
	@Mock
	SelectorFunction<Flash> flashModeSelector;

	InitialParametersProvider testee;

	@Before
	public void setUp() throws Exception {
		testee = new InitialParametersProvider(
				cameraDevice,
				focusModeSelector,
				flashModeSelector
		);

		given(cameraDevice.getCapabilities())
				.willReturn(new Capabilities(
						FOCUS_MODES,
						FLASH
				));

		given(focusModeSelector.select(FOCUS_MODES))
				.willReturn(FocusMode.FIXED);
		given(flashModeSelector.select(FLASH))
				.willReturn(Flash.AUTO_RED_EYE);
	}

	@Test
	public void selectFocusMode() throws Exception {
		// When
		Parameters parameters = testee.initialParameters();

		// Then
		assertEquals(
				FocusMode.FIXED,
				parameters.getValue(Parameters.Type.FOCUS_MODE)
		);
	}

	@Test
	public void selectFlashMode() throws Exception {
		// When
		Parameters parameters = testee.initialParameters();

		// Then
		assertEquals(
				Flash.AUTO_RED_EYE,
				parameters.getValue(Parameters.Type.FLASH)
		);
	}
}