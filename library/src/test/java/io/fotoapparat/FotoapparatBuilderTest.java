package io.fotoapparat;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.provider.CameraProvider;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatBuilderTest {

	@Mock
	Context context;
	@Mock
	CameraProvider cameraProvider;
	@Mock
	CameraRenderer cameraRenderer;
	@Mock
	SelectorFunction<Size> photoSizeSelector;
	@Mock
	SelectorFunction<LensPosition> lensPositionSelector;

	@Test
	public void onlyMandatoryParameters() throws Exception {
		// Given
		FotoapparatBuilder builder = builderWithMandatoryArguments();

		// When
		Fotoapparat result = builder.build();

		// Then
		assertNotNull(result);
	}

	@Test
	public void cameraProvider_HasDefault() throws Exception {
		// When
		FotoapparatBuilder builder = builderWithMandatoryArguments();

		// Then
		assertNotNull(builder.cameraProvider);
	}

	@Test
	public void cameraProvider_IsConfigurable() throws Exception {
		// When
		FotoapparatBuilder builder = builderWithMandatoryArguments()
				.cameraProvider(cameraProvider);

		// Then
		assertEquals(
				cameraProvider,
				builder.cameraProvider
		);
	}

	@Test(expected = IllegalStateException.class)
	public void rendererIsMandatory() throws Exception {
		// Given
		FotoapparatBuilder builder = new FotoapparatBuilder(context)
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
		FotoapparatBuilder builder = new FotoapparatBuilder(context)
				.photoSize(photoSizeSelector)
				.into(cameraRenderer);

		// When
		builder.build();

		// Then
		// Expect exception
	}

	private FotoapparatBuilder builderWithMandatoryArguments() {
		return new FotoapparatBuilder(context)
				.lensPosition(lensPositionSelector)
				.into(cameraRenderer);
	}

}