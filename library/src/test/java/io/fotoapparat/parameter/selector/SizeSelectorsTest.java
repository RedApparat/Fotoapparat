package io.fotoapparat.parameter.selector;

import org.junit.Test;

import java.util.List;

import io.fotoapparat.parameter.Size;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

public class SizeSelectorsTest {

	@Test
	public void getLargestSize() throws Exception {
		// Given
		List<Size> availableSizes = asList(
				new Size(4032, 3024),
				new Size(7680, 4320),
				new Size(1280, 720)
		);

		// When
		Size result = SizeSelectors
				.biggestSize()
				.select(availableSizes);

		// Then
		assertEquals(
				new Size(7680, 4320),
				result
		);
	}

}