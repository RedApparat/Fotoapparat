package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import io.fotoapparat.parameter.Flash;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@RunWith(MockitoJUnitRunner.class)
public class FlashCapabilityTest {

	@Mock
	Characteristics characteristics;
	@Mock
	CameraCharacteristics cameraCharacteristics;
	@InjectMocks
	FlashCapability testee;

	@Before
	public void setUp() throws Exception {
		given(characteristics.getCameraCharacteristics())
				.willReturn(cameraCharacteristics);

	}

	@Test
	public void noFlash_onlyOff() throws Exception {
		// Given
		given(cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE))
				.willReturn(false);

		// When
		Set<Flash> availableFlashModes = testee.availableFlashModes();

		// Then
		assertEquals(
				asSet(Flash.OFF),
				availableFlashModes
		);
	}

}