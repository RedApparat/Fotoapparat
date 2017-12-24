package io.fotoapparat.result

import io.fotoapparat.log.Logger
import io.fotoapparat.test.ImmediateExecutor
import io.fotoapparat.test.immediateFuture
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.junit.MockitoJUnitRunner
import java.io.File
import kotlin.test.assertNotNull
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class PhotoResultTest {

    @Mock
    lateinit var logger: Logger

    lateinit var pendingResult: PendingResult<Photo>

    @Before
    fun setUp() {
        pendingResult = PendingResult(
                immediateFuture(
                        Photo.empty()
                ),
                logger,
                ImmediateExecutor
        )
    }

    @Test
    fun `Convert to pending result`() {
        // Given
        val photoResult = PhotoResult(pendingResult)

        // When
        val result = photoResult.toPendingResult()

        // Then
        assertSame(
                expected = pendingResult,
                actual = result
        )
    }

    @Test
    fun `Convert to bitmap`() {
        // Given
        val pendingResult = spy(pendingResult)

        val photoResult = PhotoResult(pendingResult)

        // When
        val result = photoResult.toBitmap()

        // Then
        assertNotNull(result)
    }

    @Test
    fun `Save to file`() {
        // Given
        val pendingResult = spy(pendingResult)

        val photoResult = PhotoResult(pendingResult)

        // When
        val result = photoResult.saveToFile(File(""))

        // Then
        assertNotNull(result)
    }
}