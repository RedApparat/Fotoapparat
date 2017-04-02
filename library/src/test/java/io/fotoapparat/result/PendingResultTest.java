package io.fotoapparat.result;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.result.transformer.Transformer;
import io.fotoapparat.test.ImmediateExecutor;

import static io.fotoapparat.test.TestUtils.immediateFuture;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PendingResultTest {

	static final String RESULT = "Result";

	@Mock
	Transformer<String, Integer> transformer;

	PendingResult<String> testee;

	@Before
	public void setUp() throws Exception {
		testee = new PendingResult<>(
				immediateFuture(RESULT),
				new ImmediateExecutor()
		);
	}

	@Test
	public void transform() throws Exception {
		// Given
		given(transformer.transform(RESULT))
				.willReturn(123);

		// When
		Integer result = testee.transform(transformer)
				.await();

		// Then
		assertEquals(
				Integer.valueOf(123),
				result
		);
	}

	@Test
	public void await() throws Exception {
		// When
		String result = testee.await();

		// Then
		assertEquals(
				RESULT,
				result
		);
	}
}