package io.fotoapparat.result.transformer;

import org.junit.Test;

import io.fotoapparat.parameter.Size;

import static org.junit.Assert.assertEquals;

public class SizeTransformersTest {

    @Test
    public void originalSize() throws Exception {
        assertEquals(
                new Size(100, 200),
                SizeTransformers.originalSize().transform(new Size(100, 200))
        );
    }

    @Test
    public void scaled() throws Exception {
        assertEquals(
                new Size(50, 100),
                SizeTransformers.scaled(0.5f).transform(new Size(100, 200))
        );
    }

}