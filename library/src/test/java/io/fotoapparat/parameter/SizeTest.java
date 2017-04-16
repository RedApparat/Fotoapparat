package io.fotoapparat.parameter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SizeTest {

	@Test
	public void flip() throws Exception {
		assertEquals(
				new Size(10, 20),
				new Size(20, 10).flip()
		);
	}

}