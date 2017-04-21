package io.fotoapparat.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImmediateExecutorTest {

    @Mock
    Runnable runnable;

    ImmediateExecutor testee = new ImmediateExecutor();

    @Test
    public void execute() throws Exception {
        // When
        testee.execute(runnable);

        // Then
        verify(runnable).run();
    }

}