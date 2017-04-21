package io.fotoapparat.log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class CompositeLoggerTest {

    @Mock
    Logger loggerA;
    @Mock
    Logger loggerB;

    CompositeLogger testee;

    @Before
    public void setUp() throws Exception {
        testee = new CompositeLogger(asList(
                loggerA,
                loggerB
        ));
    }

    @Test
    public void log() throws Exception {
        // When
        testee.log("message");

        // Then
        InOrder inOrder = inOrder(loggerA, loggerB);

        inOrder.verify(loggerA).log("message");
        inOrder.verify(loggerB).log("message");
    }
}