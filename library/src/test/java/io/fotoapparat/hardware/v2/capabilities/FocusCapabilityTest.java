package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import io.fotoapparat.parameter.FocusMode;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class FocusCapabilityTest {

	@Mock
	Characteristics characteristics;
	@Mock
	CameraCharacteristics cameraCharacteristics;
	@InjectMocks
	FocusCapability testee;

	@Before
	public void setUp() throws Exception {
		given(characteristics.getCameraCharacteristics())
				.willReturn(cameraCharacteristics);

	}

	@Test
	public void testAvailability() throws Exception {
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
		Set<FocusMode> availableFlashModes = testee.availableFocusModes();

		// Then
		assertEquals(
				asSet(
						FocusMode.FIXED,
						FocusMode.AUTO,
						FocusMode.MACRO,
						FocusMode.CONTINUOUS_FOCUS,
						FocusMode.EDOF
				),
				availableFlashModes
		);

	}

	@Test
	public void invalidToFocusConversion_fixedDefault() throws Exception {
		// When
		FocusMode focusMode = FocusCapability.afModeToFocus(-1);

		// Then
		assertEquals(FocusMode.FIXED, focusMode);
	}

	@Test
	public void afAutoMode_FocusAutoMode() throws Exception {
		// When
		FocusMode focusMode = FocusCapability.afModeToFocus(CameraMetadata.CONTROL_AF_MODE_AUTO);

		// Then
		assertEquals(FocusMode.AUTO, focusMode);
	}

	@Test
	public void invalidToAfModeConversion_fixedDefault() throws Exception {

		// When
		int focusMode = FocusCapability.focusToAfMode(null);

		// Then
		assertEquals(CameraMetadata.CONTROL_AF_MODE_OFF, focusMode);
	}

	@Test
	public void edofFocusMode_edofAutoMode() throws Exception {
		// Given

		// When
		FocusMode focusMode = FocusCapability.afModeToFocus(CameraMetadata.CONTROL_AF_MODE_EDOF);

		// Then
		assertEquals(FocusMode.EDOF, focusMode);
	}

}