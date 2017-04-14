package io.fotoapparat.hardware.v2.capabilities;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@RunWith(MockitoJUnitRunner.class)
public class CapabilitiesFactoryTest {
	private static final Set<Size> SIZE_SET = asSet(
			new Size(4032, 3024),
			new Size(7680, 4320),
			new Size(1280, 720)
	);
	private static final Set<Flash> FLASH_SET = asSet(
			Flash.OFF,
			Flash.TORCH,
			Flash.ON,
			Flash.AUTO,
			Flash.AUTO_RED_EYE
	);
	private static final Set<FocusMode> FOCUS_MODE_SET = asSet(
			FocusMode.FIXED,
			FocusMode.AUTO,
			FocusMode.MACRO,
			FocusMode.CONTINUOUS_FOCUS,
			FocusMode.EDOF
	);

	@Mock
	SizeCapability sizeCapability;
	@Mock
	FlashCapability flashCapability;
	@Mock
	FocusCapability focusCapability;
	@InjectMocks
	CapabilitiesFactory testee;

	@Test
	public void testSets() throws Exception {
		// Given
		given(sizeCapability.availableJpegSizes())
				.willReturn(SIZE_SET);

		given(flashCapability.availableFlashModes())
				.willReturn(FLASH_SET);

		given(focusCapability.availableFocusModes())
				.willReturn(FOCUS_MODE_SET);

		// When
		Capabilities capabilities = testee.getCapabilities();

		// Then
		assertEquals(SIZE_SET, capabilities.supportedSizes());
		assertEquals(FOCUS_MODE_SET, capabilities.supportedFocusModes());
		assertEquals(FLASH_SET, capabilities.supportedFlashModes());

	}
}