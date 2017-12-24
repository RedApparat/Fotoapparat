package io.fotoapparat.result

import io.fotoapparat.log.Logger
import io.fotoapparat.test.ImmediateExecutor
import io.fotoapparat.test.immediateFuture
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Future
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class PendingResultTest {

    val result = "Result"

    @Mock
    lateinit var logger: Logger

    lateinit var testee: PendingResult<String>

    @Before
    fun setUp() {
        testee = PendingResult(
                future = immediateFuture(result),
                logger = logger,
                executor = ImmediateExecutor
        )
    }

    @Test
    fun Transform() {
        // Given
        val transformer: String.() -> Int = {
            123
        }

        // When
        val result = testee.transform(transformer)
                .await()

        // Then
        assertEquals(
                expected = 123,
                actual = result
        )
    }

    @Test
    fun Adapt() {
        // Given
        val adapter: Future<String>.() -> Int = {
            123
        }

        // When
        val result = testee.adapt(adapter)

        // Then
        assertEquals(
                expected = 123,
                actual = result
        )
    }

    @Test
    fun Await() {
        // When
        val result = testee.await()

        // Then
        assertEquals(
                expected = result,
                actual = result
        )
    }


}