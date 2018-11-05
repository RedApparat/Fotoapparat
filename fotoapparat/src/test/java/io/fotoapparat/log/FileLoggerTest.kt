package io.fotoapparat.log

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

internal class FileLoggerTest {

    internal val file = File("test")
    lateinit var testee: FileLogger

    @Before
    fun setUp() {
        file.ensureFileDeleted()

        testee = FileLogger(file)
    }

    @Test
    fun `Write message`() {
        // When
        testee.log("message")
        testee.log("message")

        // Then
        assertEquals(
                "message\nmessage\n",
                file.readText()
        )
    }

    @Test
    fun `Ignore exceptions`() {
        // Given
        val logger = FileLogger(File("/"))

        // When
        logger.log("message")

        // Then
        // No exception is thrown
    }

    @After
    fun tearDown() {
        file.ensureFileDeleted()
    }

}

private fun File.ensureFileDeleted() {
    if (exists() && !delete()) {
        throw IllegalStateException("Test file still exists: " + this)
    }
}
