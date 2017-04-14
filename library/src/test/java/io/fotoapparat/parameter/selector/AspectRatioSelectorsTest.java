package io.fotoapparat.parameter.selector;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Set;

import io.fotoapparat.parameter.AspectRatio;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class AspectRatioSelectorsTest {

	@InjectMocks
	AspectRatioSelectors testee;

	@Test
	public void standard_Available() throws Exception {
		// Given
		Set<AspectRatio> availableModes = asSet(
				AspectRatio.STANDARD_4_3,
				AspectRatio.WIDE_16_9
		);

		// When
		AspectRatio result = AspectRatioSelectors
				.standard()
				.select(availableModes);

		// Then
		assertEquals(
				AspectRatio.STANDARD_4_3,
				result
		);
	}

	@Test
	public void standard_NotAvailable() throws Exception {
		// Given
		Set<AspectRatio> availableModes = asSet(
				AspectRatio.WIDE_16_9
		);

		// When
		AspectRatio result = AspectRatioSelectors
				.standard()
				.select(availableModes);

		// Then
		assertNull(result);
	}

	@Test
	public void wide_Available() throws Exception {
		// Given
		Set<AspectRatio> availableModes = asSet(
				AspectRatio.STANDARD_4_3,
				AspectRatio.WIDE_16_9
		);

		// When
		AspectRatio result = AspectRatioSelectors
				.wide()
				.select(availableModes);

		// Then
		assertEquals(
				AspectRatio.WIDE_16_9,
				result
		);
	}

	@Test
	public void wide_NotAvailable() throws Exception {
		// Given
		Set<AspectRatio> availableModes = asSet(
				AspectRatio.STANDARD_4_3
		);

		// When
		AspectRatio result = AspectRatioSelectors
				.wide()
				.select(availableModes);

		// Then
		assertNull(result);
	}

}