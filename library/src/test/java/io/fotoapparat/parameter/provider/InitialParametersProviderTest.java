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
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InitialParametersProviderTest {

	static final Size PHOTO_SIZE = new Size(4000, 3000);
	static final Size PREVIEW_SIZE = new Size(2000, 1500);
	static final Size PREVIEW_SIZE_WRONG_ASPECT_RATIO = new Size(1000, 1000);

	static final Set<FocusMode> FOCUS_MODES = asSet(FocusMode.FIXED);
	static final Set<Flash> FLASH = asSet(Flash.AUTO_RED_EYE);

	static final Set<Size> PHOTO_SIZES = asSet(PHOTO_SIZE);
	static final Set<Size> VALID_PREVIEW_SIZES = asSet(PREVIEW_SIZE);
	static final Set<Size> ALL_PREVIEW_SIZES = asSet(
			PREVIEW_SIZE,
			PREVIEW_SIZE_WRONG_ASPECT_RATIO
	);

	@Mock
	CameraDevice cameraDevice;
	@Mock
	SelectorFunction<Size> photoSizeSelector;
	@Mock
	SelectorFunction<Size> previewSizeSelector;
	@Mock
	SelectorFunction<FocusMode> focusModeSelector;
	@Mock
	SelectorFunction<Flash> flashModeSelector;

	InitialParametersProvider testee;

	@Before
	public void setUp() throws Exception {
		testee = new InitialParametersProvider(
				cameraDevice,
				photoSizeSelector,
				previewSizeSelector,
				focusModeSelector,
				flashModeSelector
		);

		given(cameraDevice.getCapabilities())
				.willReturn(new Capabilities(
						PHOTO_SIZES,
						ALL_PREVIEW_SIZES,
						FOCUS_MODES,
						FLASH
				));

		given(photoSizeSelector.select(PHOTO_SIZES))
				.willReturn(PHOTO_SIZE);
		given(previewSizeSelector.select(VALID_PREVIEW_SIZES))
				.willReturn(PREVIEW_SIZE);
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

	@Test
	public void selectPhotoSize() throws Exception {
		// When
		Parameters parameters = testee.initialParameters();

		// Then
		assertEquals(
				PHOTO_SIZE,
				parameters.getValue(Parameters.Type.PICTURE_SIZE)
		);
	}

	@Test
	public void selectPreviewSize_WithValidAspectRatio() throws Exception {
		// When
		Parameters parameters = testee.initialParameters();

		// Then
		verify(previewSizeSelector).select(VALID_PREVIEW_SIZES);

		assertEquals(
				PREVIEW_SIZE,
				parameters.getValue(Parameters.Type.PREVIEW_SIZE)
		);
	}

}