package io.fotoapparat.hardware;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;

import static io.fotoapparat.test.TestUtils.asSet;
import static java.util.Collections.emptySet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParametersTest {

	Parameters testee;

	@Before
	public void setUp() throws Exception {
		testee = new Parameters();
	}

	@Test
	public void nullByDefault() throws Exception {
		// When
		Object value = testee.getValue(Parameters.Type.FOCUS_MODE);

		// Then
		assertNull(value);
	}

	@Test
	public void readAndWrite() throws Exception {
		// When
		testee.putValue(Parameters.Type.FOCUS_MODE, FocusMode.AUTO);
		Object value = testee.getValue(Parameters.Type.FOCUS_MODE);

		// Then
		assertEquals(
				FocusMode.AUTO,
				value
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void putValue_TypeMismatch() throws Exception {
		// When
		testee.putValue(Parameters.Type.PICTURE_SIZE, FocusMode.AUTO);

		// Then
		// Expect exception
	}

	@Test
	public void putValue_Null() throws Exception {
		// Given
		testee.putValue(Parameters.Type.FLASH, null);

		// When
		Object value = testee.getValue(Parameters.Type.FLASH);

		// Then
		assertNull(value);
	}

	@Test
	public void keysStoredSeparately() throws Exception {
		// Given
		testee.putValue(Parameters.Type.PICTURE_SIZE, new Size(100, 100));

		// When
		Object value = testee.getValue(Parameters.Type.FOCUS_MODE);

		// Then
		assertNull(value);
	}

	@Test
	public void storedTypes() throws Exception {
		// Given
		testee.putValue(Parameters.Type.FOCUS_MODE, FocusMode.AUTO);
		testee.putValue(Parameters.Type.PICTURE_SIZE, new Size(100, 100));
		testee.putValue(Parameters.Type.FLASH, null);

		// When
		Set<Parameters.Type> result = testee.storedTypes();

		// Then
		assertEquals(
				asSet(
						Parameters.Type.FOCUS_MODE,
						Parameters.Type.PICTURE_SIZE
				),
				result
		);
	}

	@Test
	public void storedTypes_EmptyByDefault() throws Exception {
		// When
		Set<Parameters.Type> result = testee.storedTypes();

		// Then
		assertEquals(
				emptySet(),
				result
		);
	}

}