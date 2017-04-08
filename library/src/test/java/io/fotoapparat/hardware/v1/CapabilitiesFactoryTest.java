package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v1.capabilities.CapabilitiesFactory;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;

import static io.fotoapparat.test.TestUtils.asSet;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("deprecation")
public class CapabilitiesFactoryTest {

	@Mock
	Camera.Parameters parameters;

	CapabilitiesFactory testee;

	@Before
	public void setUp() throws Exception {
		given(parameters.getSupportedFocusModes())
				.willReturn(Collections.<String>emptyList());
		given(parameters.getSupportedFlashModes())
				.willReturn(Collections.<String>emptyList());

		testee = new CapabilitiesFactory();
	}

	@Test
	public void mapFocusModes() throws Exception {
		// Given
		given(parameters.getSupportedFocusModes())
				.willReturn(asList(
						Camera.Parameters.FOCUS_MODE_AUTO,
						Camera.Parameters.FOCUS_MODE_FIXED,
						Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE,
						Camera.Parameters.FOCUS_MODE_INFINITY,
						Camera.Parameters.FOCUS_MODE_MACRO,
						"something random"
				));

		// When
		Capabilities capabilities = testee.fromParameters(parameters);

		// Then
		assertEquals(
				asSet(
						FocusMode.AUTO,
						FocusMode.FIXED,
						FocusMode.CONTINUOUS_FOCUS,
						FocusMode.INFINITY,
						FocusMode.MACRO
				),
				capabilities.supportedFocusModes()
		);
	}

	@Test
	public void mapFlashModes() throws Exception {
		// Given
		given(parameters.getSupportedFlashModes())
				.willReturn(asList(
						Camera.Parameters.FLASH_MODE_AUTO,
						Camera.Parameters.FLASH_MODE_ON,
						Camera.Parameters.FLASH_MODE_RED_EYE,
						Camera.Parameters.FLASH_MODE_TORCH,
						Camera.Parameters.FLASH_MODE_OFF
				));

		// When
		Capabilities capabilities = testee.fromParameters(parameters);

		// Then
		assertEquals(
				asSet(
						Flash.AUTO,
						Flash.SINGLE,
						Flash.AUTO_RED_EYE,
						Flash.TORCH,
						Flash.OFF
				),
				capabilities.supportedFlashModes()
		);
	}
}