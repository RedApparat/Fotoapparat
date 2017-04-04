package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.FocusMode;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@RunWith(MockitoJUnitRunner.class)
public class CapabilitiesFactoryTest {

	@Mock
	Characteristics characteristics;
	@Mock
	CameraCharacteristics cameraCharacteristics;
	@InjectMocks
	CapabilitiesFactory testee;

	@Before
	public void setUp() throws Exception {
		given(characteristics.getCameraCharacteristics())
				.willReturn(cameraCharacteristics);
	}

	@Test
	public void testFocusSet() throws Exception {
		// Given
		given(cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES))
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
		assertEquals(
				asSet(
						FocusMode.FIXED,
						FocusMode.AUTO,
						FocusMode.MACRO,
						FocusMode.CONTINUOUS_FOCUS,
						FocusMode.EDOF
				),
				capabilities.supportedFocusModes()
		);

	}
}