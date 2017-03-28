package io.fotoapparat.parameter.selector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SelectorsTest {

	@Mock
	SelectorFunction<String> functionA;
	@Mock
	SelectorFunction<String> functionB;

	@Test
	public void firstAvailable() throws Exception {
		// Given
		List<String> options = asList("B", "C");

		given(functionA.select(options))
				.willReturn(null);

		given(functionB.select(options))
				.willReturn("B");

		// When
		String result = Selectors
				.firstAvailable(
						functionA,
						functionB
				)
				.select(options);

		// Then
		assertEquals("B", result);
	}

}