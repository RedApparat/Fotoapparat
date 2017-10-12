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
import static junit.framework.Assert.fail;
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
    public void standardRatioWithTolerance() throws Exception {
        // Given
        given(sizeSelector.select(ArgumentMatchers.<Size>anyCollection()))
                .willReturn(new Size(400, 300));

        // When
        Size result = AspectRatioSelectors
                .standardRatio(sizeSelector, 0.1)
                .select(asList(
                        new Size(400, 300),
                        new Size(410, 300),
                        new Size(800, 600),
                        new Size(790, 600),
                        new Size(100, 100),
                        new Size(160, 90)
                ));

        // Then
        assertEquals(
                new Size(400, 300),
                result
        );

        verify(sizeSelector).select(asSet(
                new Size(400, 300),
                new Size(410, 300),
                new Size(800, 600),
                new Size(790, 600)
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

    @Test
    public void wideRatioWithTolerance() throws Exception {
        // Given
        given(sizeSelector.select(ArgumentMatchers.<Size>anyCollection()))
                .willReturn(new Size(16, 9));

        // When
        Size result = AspectRatioSelectors
                .wideRatio(sizeSelector, 0.1)
                .select(asList(
                        new Size(16, 9),
                        new Size(16, 10),
                        new Size(32, 18),
                        new Size(32, 20),
                        new Size(10, 10)
                ));

        // Then
        assertEquals(
                new Size(16, 9),
                result
        );

        verify(sizeSelector).select(asSet(
                new Size(16, 9),
                new Size(16, 10),
                new Size(32, 18),
                new Size(32, 20)
        ));
    }

    @Test
    public void ratioWithTolerance() throws Exception {
        // Given
        given(sizeSelector.select(ArgumentMatchers.<Size>anyCollection()))
                .willReturn(new Size(110, 100));

        // When
        Size result = AspectRatioSelectors
                .aspectRatio(1.0f, sizeSelector, 0.1)
                .select(asList(
                        new Size(16, 9),
                        new Size(110, 100),
                        new Size(105, 100),
                        new Size(95, 100),
                        new Size(90, 100),
                        new Size(4, 3),
                        new Size(20, 10)
                ));

        // Then
        assertEquals(
                new Size(110, 100),
                result
        );

        verify(sizeSelector).select(asSet(
                new Size(110, 100),
                new Size(105, 100),
                new Size(95, 100),
                new Size(90, 100)
        ));
    }

    @Test
    public void ratioWithNegativeToleranceThrows() throws Exception {
        // Given
        final double NEGATIVE_TOLERANCE = -0.1;
        final double ABOVE_RANGE_TOLERANCE = 1.1;

        try {
            // When
            AspectRatioSelectors
                    .aspectRatio(1.0f, sizeSelector, NEGATIVE_TOLERANCE)
                    .select(asList(
                            new Size(16, 9),
                            new Size(16, 10)));
            fail("Negative aspect ratio tolerance is illegal");
        } catch (IllegalArgumentException ex) {
            // Then exception is throws
        }


        try {
            // When
            AspectRatioSelectors
                    .aspectRatio(1.0f, sizeSelector, ABOVE_RANGE_TOLERANCE)
                    .select(asList(
                            new Size(16, 9),
                            new Size(16, 10)));
            fail("Aspect ratio tolerance >1.0 is illegal");
        } catch (IllegalArgumentException ex) {
            // Then exception is throws
        }
    }
}