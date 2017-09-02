package io.fotoapparat.parameter.selector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;

import io.fotoapparat.parameter.Size;

import static io.fotoapparat.test.TestUtils.asSet;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AspectRatioSelectorsTest {

    @Mock
    SelectorFunction<Collection<Size>, Size> sizeSelector;

    @Test
    public void standardRatio() throws Exception {
        // Given
        given(sizeSelector.select(ArgumentMatchers.<Size>anyCollection()))
                .willReturn(new Size(4, 3));

        // When
        Size result = AspectRatioSelectors
                .standardRatio(sizeSelector)
                .select(asList(
                        new Size(4, 3),
                        new Size(8, 6),
                        new Size(10, 10)
                ));

        // Then
        assertEquals(
                new Size(4, 3),
                result
        );

        verify(sizeSelector).select(asSet(
                new Size(4, 3),
                new Size(8, 6)
        ));
    }

    @Test
    public void wideRatio() throws Exception {
        // Given
        given(sizeSelector.select(ArgumentMatchers.<Size>anyCollection()))
                .willReturn(new Size(16, 9));

        // When
        Size result = AspectRatioSelectors
                .wideRatio(sizeSelector)
                .select(asList(
                        new Size(16, 9),
                        new Size(32, 18),
                        new Size(10, 10)
                ));

        // Then
        assertEquals(
                new Size(16, 9),
                result
        );

        verify(sizeSelector).select(asSet(
                new Size(16, 9),
                new Size(32, 18)
        ));
    }

}