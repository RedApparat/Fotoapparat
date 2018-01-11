package io.fotoapparat.result.transformer

import io.fotoapparat.exif.ExifOrientationWriter
import io.fotoapparat.result.Photo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
internal class SaveToFileTransformerTest {

    val file = File("test")
    @Mock
    lateinit var exifOrientationWriter: ExifOrientationWriter
    lateinit var testee: SaveToFileTransformer

    @Before
    fun setUp() {
        file.ensureDeleted()

        testee = SaveToFileTransformer(
                file,
                exifOrientationWriter
        )
    }

    @Test
    fun `Save photo`() {
        // Given
        val photo = Photo(
                encodedImage = byteArrayOf(1, 2, 3),
                rotationDegrees = 0
        )

        // When
        testee(photo)

        // Then
        assertTrue(file.exists())
        assertEquals(
                expected = photo.encodedImage.size.toLong(),
                actual = file.length()
        )

        verify(exifOrientationWriter).writeExifOrientation(
                file,
                photo.rotationDegrees
        )
    }

    @After
    fun tearDown() {
        file.ensureDeleted()
    }

}

private fun File.ensureDeleted() {
    if (exists() && !delete()) {
        throw IllegalStateException("Can't delete test file")
    }
}
