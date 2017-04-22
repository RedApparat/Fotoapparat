package io.fotoapparat.parameter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SizeTest {

    @Test
    public void getAspectRatio() throws Exception {
        assertEquals(
                2.5f,
                new Size(250, 100).getAspectRatio(),
                1e-6
        );
    }

    @Test
    public void getAspectRatio_EmptySize() throws Exception {
        assertEquals(
                Float.NaN,
                new Size(0, 0).getAspectRatio(),
                1e-6
        );
    }

    @Test
    public void flip() throws Exception {
        assertEquals(
                new Size(10, 20),
                new Size(20, 10).flip()
        );
    }

}