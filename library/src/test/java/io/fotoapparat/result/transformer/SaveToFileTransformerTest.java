package io.fotoapparat.result.transformer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import io.fotoapparat.photo.Photo;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class SaveToFileTransformerTest {

    static final File FILE = new File("test");

    SaveToFileTransformer testee;

    @Before
    public void setUp() throws Exception {
        ensureFileDeleted();

        testee = new SaveToFileTransformer(FILE);
    }

    @Test
    public void savePhoto() throws Exception {
        // Given
        Photo photo = new Photo(
                new byte[]{1, 2, 3},
                0
        );

        // When
        testee.transform(photo);

        // Then
        assertTrue(FILE.exists());
        assertEquals(
                photo.encodedImage.length,
                FILE.length()
        );
    }

    @After
    public void tearDown() throws Exception {
        ensureFileDeleted();
    }

    private void ensureFileDeleted() {
        if (FILE.exists() && !FILE.delete()) {
            throw new IllegalStateException("Can't delete test file");
        }
    }
}