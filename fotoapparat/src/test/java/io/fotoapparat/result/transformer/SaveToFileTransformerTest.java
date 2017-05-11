package io.fotoapparat.result.transformer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import io.fotoapparat.photo.Photo;
import io.fotoapparat.util.ExifOrientationWriter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SaveToFileTransformerTest {

    static final File FILE = new File("test");

    @Mock
    ExifOrientationWriter exifOrientationWriter;

    SaveToFileTransformer testee;

    @Before
    public void setUp() throws Exception {
        ensureFileDeleted();

        testee = new SaveToFileTransformer(
                FILE,
                exifOrientationWriter
        );
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

        verify(exifOrientationWriter).writeExifOrientation(FILE, photo);
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