package io.fotoapparat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

import static junit.framework.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatBuilderTest {

	@Mock
	CameraRenderer cameraRenderer;
	@Mock
	SelectorFunction<Size> photoSizeSelector;
	@Mock
	SelectorFunction<LensPosition> lensPositionSelector;

	@Test
	public void onlyMandatoryParameters() throws Exception {
		// Given
		FotoapparatBuilder builder = new FotoapparatBuilder()
				.lensPosition(lensPositionSelector)
				.into(cameraRenderer);

		// When
		Fotoapparat result = builder.build();

		// Then
		assertNotNull(result);
	}

	@Test(expected = IllegalStateException.class)
	public void rendererIsMandatory() throws Exception {
		// Given
		FotoapparatBuilder builder = new FotoapparatBuilder()
				.photoSize(photoSizeSelector)
				.lensPosition(lensPositionSelector);

		// When
		builder.build();

		// Then
		// Expect exception
	}

	@Test(expected = IllegalStateException.class)
	public void lensPositionIsMandatory() throws Exception {
		// Given
		FotoapparatBuilder builder = new FotoapparatBuilder()
				.photoSize(photoSizeSelector)
				.into(cameraRenderer);

		// When
		builder.build();

		// Then
		// Expect exception
	}

}