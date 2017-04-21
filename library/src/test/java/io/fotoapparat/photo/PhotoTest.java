package io.fotoapparat.photo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhotoTest {

    @Test
    public void empty() throws Exception {
        // When
        Photo result = Photo.empty();

        // Then
        assertEquals(
                0,
                result.encodedImage.length
        );

        assertEquals(
                0,
                result.rotationDegrees
        );
    }
}