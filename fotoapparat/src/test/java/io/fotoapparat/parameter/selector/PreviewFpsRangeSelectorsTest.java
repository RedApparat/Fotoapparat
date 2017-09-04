package io.fotoapparat.parameter.selector;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.range.Ranges;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PreviewFpsRangeSelectorsTest {

    @Test
    public void fromExactFps_Available() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                Ranges.continuousRange(24000, 24000),
                Ranges.continuousRange(30000, 30000),
                Ranges.continuousRange(40000, 40000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .fromExactFps(30)
                .select(availableRanges);

        // Then
        assertEquals(
                Ranges.continuousRange(30000, 30000),
                result
        );
    }

    @Test
    public void fromExactFps_NotAvailable() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                Ranges.continuousRange(24000, 30000),
                Ranges.continuousRange(30000, 36000),
                Ranges.continuousRange(40000, 40000)
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
                Ranges.continuousRange(24000, 30000),
                Ranges.continuousRange(30000, 30000),
                Ranges.continuousRange(30000, 34000),
                Ranges.continuousRange(30000, 36000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .nearestToExactFps(30)
                .select(availableRanges);

        // Then
        assertEquals(
                Ranges.continuousRange(30000, 30000),
                result
        );
    }

    @Test
    public void nearestToExactFps_NoExactAvailable() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                Ranges.continuousRange(24000, 30000),
                Ranges.continuousRange(30000, 34000),
                Ranges.continuousRange(30000, 36000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .nearestToExactFps(30)
                .select(availableRanges);

        // Then
        assertEquals(
                Ranges.continuousRange(30000, 36000),
                result
        );
    }

    @Test
    public void rangeWithHighestFps() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                Ranges.continuousRange(24000, 30000),
                Ranges.continuousRange(30000, 34000),
                Ranges.continuousRange(30000, 36000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .rangeWithHighestFps()
                .select(availableRanges);

        // Then
        assertEquals(
                Ranges.continuousRange(30000, 36000),
                result
        );
    }

    @Test
    public void rangeWithLowestFps() throws Exception {
        // Given
        List<Range<Integer>> availableRanges = Arrays.<Range<Integer>>asList(
                Ranges.continuousRange(24000, 30000),
                Ranges.continuousRange(30000, 34000),
                Ranges.continuousRange(30000, 36000)
        );

        // When
        Range<Integer> result = PreviewFpsRangeSelectors
                .rangeWithLowestFps()
                .select(availableRanges);

        // Then
        assertEquals(
                Ranges.continuousRange(24000, 30000),
                result
        );
    }
}
