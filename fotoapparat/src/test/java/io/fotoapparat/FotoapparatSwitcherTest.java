package io.fotoapparat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatSwitcherTest {

    @Mock
    Fotoapparat fotoapparatA;
    @Mock
    Fotoapparat fotoapparatB;

    FotoapparatSwitcher testee;

    @Before
    public void setUp() throws Exception {
        testee = FotoapparatSwitcher.withDefault(fotoapparatA);
    }

    @Test
    public void start_Default() throws Exception {
        // When
        testee.start();

        // Then
        verify(fotoapparatA).start();
    }

    @Test
    public void stop_Default() throws Exception {
        // When
        testee.stop();

        // Then
        verify(fotoapparatA).stop();
    }

    @Test
    public void getCurrentFotoapparat() throws Exception {
        // When
        Fotoapparat result = testee.getCurrentFotoapparat();

        // Then
        assertSame(fotoapparatA, result);
    }

    @Test
    public void switchTo() throws Exception {
        // Given
        testee.switchTo(fotoapparatB);

        // When
        Fotoapparat result = testee.getCurrentFotoapparat();

        // Then
        assertSame(fotoapparatB, result);

        verifyZeroInteractions(fotoapparatA);
        verifyZeroInteractions(fotoapparatB);
    }

    @Test
    public void switchTo_AlreadyStarted() throws Exception {
        // Given
        testee.start();

        // When
        testee.switchTo(fotoapparatB);

        // Then
        InOrder inOrder = inOrder(fotoapparatA, fotoapparatB);

        inOrder.verify(fotoapparatA).stop();
        inOrder.verify(fotoapparatB).start();
    }

}