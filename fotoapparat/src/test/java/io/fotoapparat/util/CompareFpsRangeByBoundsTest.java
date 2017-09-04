package io.fotoapparat.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.range.Ranges;

public class CompareFpsRangeByBoundsTest {

    CompareFpsRangeByBounds comparator = new CompareFpsRangeByBounds();

    @Test
    public void findMaxFpsRange() throws Exception {
        // Given
        List<Range<Integer>> fpsRangesList = Arrays.<Range<Integer>>asList(
                Ranges.continuousRange(24000, 24000),
                Ranges.continuousRange(24000, 30000),
                Ranges.continuousRange(30000, 30000),
                Ranges.continuousRange(30000, 36000),
                Ranges.continuousRange(30000, 34000)
        );

        // When
        Range<Integer> maxRange = Collections.max(fpsRangesList, comparator);

        // Then
        Assert.assertEquals(
                maxRange,
                Ranges.continuousRange(30000, 36000)
        );
    }

    @Test
    public void findMinFpsRange() throws Exception {
        // Given
        List<Range<Integer>> fpsRangesList = Arrays.<Range<Integer>>asList(
                Ranges.continuousRange(24000, 24000),
                Ranges.continuousRange(24000, 30000),
                Ranges.continuousRange(30000, 30000),
                Ranges.continuousRange(20000, 26000),
                Ranges.continuousRange(20000, 28000)
        );

        // When
        Range<Integer> minRange = Collections.min(fpsRangesList, comparator);

        // Then
        Assert.assertEquals(
                minRange,
                Ranges.continuousRange(20000, 26000)
        );
    }
}
