package io.fotoapparat.log

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class CompositeLoggerTest {

    @Mock
    lateinit var loggerA: Logger
    @Mock
    lateinit var loggerB: Logger

    @Test
    fun log() {
        // Given
        val testee = CompositeLogger(listOf(
                loggerA,
                loggerB
        ))

        // When
        testee.log("message")

        // Then
        val inOrder = inOrder(loggerA, loggerB)

        inOrder.verify(loggerA).log("message")
        inOrder.verify(loggerB).log("message")
    }
}