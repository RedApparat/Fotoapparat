package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraMetadata;
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

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@RunWith(MockitoJUnitRunner.class)
public class CapabilitiesFactoryTest {
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
	Characteristics characteristics;
	@InjectMocks
	CapabilitiesFactory testee;

	@Test
	public void testSets() throws Exception {
		// Given
		given(characteristics.getJpegOutputSizes())
				.willReturn(new android.util.Size[]{});

		given(characteristics.isFlashAvailable())
				.willReturn(true);
		given(characteristics.autoExposureModes())
				.willReturn(new int[]{
						CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH,
						CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH,
						CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE
				});

		given(characteristics.autoFocusModes())
				.willReturn(
						new int[]{
								CameraMetadata.CONTROL_AF_MODE_OFF,
								CameraMetadata.CONTROL_AF_MODE_AUTO,
								CameraMetadata.CONTROL_AF_MODE_MACRO,
								CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_VIDEO,
								CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE,
								CameraMetadata.CONTROL_AF_MODE_EDOF
						}
				);

		// When
		Capabilities capabilities = testee.getCapabilities();

		// Then
		assertEquals(FOCUS_MODE_SET, capabilities.supportedFocusModes());
		assertEquals(FLASH_SET, capabilities.supportedFlashModes());

	}
}