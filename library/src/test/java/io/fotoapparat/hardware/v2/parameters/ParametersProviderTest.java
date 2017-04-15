package io.fotoapparat.hardware.v2.parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ParametersProviderTest {

	@Mock
	Parameters parameters;

	@InjectMocks
	ParametersProvider testee;

	@Test
	public void getFlash() throws Exception {
		// Given
		given(parameters.getValue(Parameters.Type.FLASH))
				.willReturn(Flash.OFF);

		testee.updateParameters(parameters);

		// When
		Flash flash = testee.getFlash();

		// Then
		assertEquals(Flash.OFF, flash);
	}

	@Test
	public void getFocus() throws Exception {
		// Given
		given(parameters.getValue(Parameters.Type.FOCUS_MODE))
				.willReturn(FocusMode.CONTINUOUS_FOCUS);

		testee.updateParameters(parameters);

		// When
		FocusMode focusMode = testee.getFocus();

		// Then
		assertEquals(FocusMode.CONTINUOUS_FOCUS, focusMode);
	}

	@Test
	public void getCaptureSize() throws Exception {
		// Given
		given(parameters.getValue(Parameters.Type.PICTURE_SIZE))
				.willReturn(new Size(1920, 1080));

		testee.updateParameters(parameters);

		// When
		Size focusMode = testee.getStillCaptureSize();
		float stillCaptureAspectRatio = testee.getStillCaptureAspectRatio();

		// Then
		assertEquals(new Size(1920, 1080), focusMode);
		assertEquals(1920f / 1080, stillCaptureAspectRatio);
	}

	@Test
	public void bigAspectRatio() throws Exception {
		// Given
		given(parameters.getValue(Parameters.Type.PICTURE_SIZE))
				.willReturn(new Size(3240, 1080));

		testee.updateParameters(parameters);

		// When
		Size previewSize = testee.getPreviewSize();

		// Then
		assertTrue(previewSize.width <= ParametersProvider.MAX_PREVIEW_WIDTH);
		assertTrue(previewSize.height <= ParametersProvider.MAX_PREVIEW_HEIGHT);
		assertEquals(3240f / 1080, (float) previewSize.width / previewSize.height);
	}

	@Test
	public void smallAspectRatio() throws Exception {
		// Given
		given(parameters.getValue(Parameters.Type.PICTURE_SIZE))
				.willReturn(new Size(1080, 3240));

		testee.updateParameters(parameters);

		// When
		Size previewSize = testee.getPreviewSize();

		// Then
		assertTrue(previewSize.width <= ParametersProvider.MAX_PREVIEW_WIDTH);
		assertTrue(previewSize.height <= ParametersProvider.MAX_PREVIEW_HEIGHT);
		assertEquals(1080f / 3240f, (float) previewSize.width / previewSize.height);
	}
}