package io.fotoapparat.test

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ImmediateExecutorTest {

    @Mock
    lateinit var runnable: Runnable

    @Test
    fun execute() {
        // When
        ImmediateExecutor.execute(runnable)

        // Then
        verify(runnable).run()
    }

}