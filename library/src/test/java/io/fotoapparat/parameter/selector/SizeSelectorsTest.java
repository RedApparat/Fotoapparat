package io.fotoapparat.parameter.selector;

import org.junit.Test;

import java.util.List;

import io.fotoapparat.parameter.AspectRatio;
import io.fotoapparat.parameter.Size;

import static io.fotoapparat.parameter.selector.AspectRatioSelectors.wideRatio;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

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

	@Test
	public void getLargest_16_9_Size() throws Exception {
		// Given
		List<Size> availableSizes = asList(
				new Size(2048, 1536), // 4:3
				new Size(1920, 1080), // 16:9
				new Size(1152, 648)   // 16:9
		);

		// When
		Size result = SizeSelectors
				.biggestSize(wideRatio())
				.select(availableSizes);

		// Then
		assertEquals(
				new Size(1920, 1080),
				result
		);
	}

	@Test
	public void getNothing() throws Exception {
		// Given
		List<Size> availableSizes = asList(
				new Size(4032, 3024),
				new Size(7680, 4320),
				new Size(1280, 720)
		);

		// When
		Size result = SizeSelectors
				.biggestSize(Selectors.<AspectRatio>nothing())
				.select(availableSizes);

		// Then
		assertNull(result);

	}
}