package io.fotoapparat.parameter.selector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import io.fotoapparat.parameter.range.IntervalRange;
import io.fotoapparat.parameter.range.Range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class PreviewFpsFrameSelectorsTest {

    @Test
    public void fromExactFps_Available() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                new IntervalRange<>(24000, 24000),
                new IntervalRange<>(30000, 30000),
                new IntervalRange<>(40000, 40000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .fromExactFps(30)
                .select(availableRanges);

        // Then
        assertEquals(
                new IntervalRange<>(30000, 30000),
                result
        );
    }

    @Test
    public void fromExactFps_NotAvailable() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                new IntervalRange<>(24000, 30000),
                new IntervalRange<>(30000, 36000),
                new IntervalRange<>(40000, 40000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .fromExactFps(30)
                .select(availableRanges);

        // Then
        assertNull(result);
    }

    @Test
    public void nearestToExactFps_ExactAvailable() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                new IntervalRange<>(24000, 30000),
                new IntervalRange<>(30000, 30000),
                new IntervalRange<>(30000, 34000),
                new IntervalRange<>(30000, 36000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .nearestToExactFps(30)
                .select(availableRanges);

        // Then
        assertEquals(
                new IntervalRange<>(30000, 30000),
                result
        );
    }

    @Test
    public void nearestToExactFps_NoExactAvailable() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                new IntervalRange<>(24000, 30000),
                new IntervalRange<>(30000, 34000),
                new IntervalRange<>(30000, 36000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .nearestToExactFps(30)
                .select(availableRanges);

        // Then
        assertEquals(
                new IntervalRange<>(30000, 36000),
                result
        );
    }

    @Test
    public void biggestFpsRange() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                new IntervalRange<>(24000, 30000),
                new IntervalRange<>(30000, 34000),
                new IntervalRange<>(30000, 36000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .biggestFpsRange()
                .select(availableRanges);

        // Then
        assertEquals(
                new IntervalRange<>(30000, 36000),
                result
        );
    }

    @Test
    public void lowestFpsRange() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                new IntervalRange<>(24000, 30000),
                new IntervalRange<>(30000, 34000),
                new IntervalRange<>(30000, 36000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .lowestFpsRange()
                .select(availableRanges);

        // Then
        assertEquals(
                new IntervalRange<>(24000, 30000),
                result
        );
    }
}
