package io.fotoapparat.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.fotoapparat.parameter.range.ContinuousRange;
import io.fotoapparat.parameter.range.Range;

public class CompareFpsRangeByBoundsTest {

    CompareFpsRangeByBounds comparator = new CompareFpsRangeByBounds();

    @Test
    public void findMaxFpsRange() throws Exception {
        // Given
        List<Range<Integer>> fpsRangesList = Arrays.<Range<Integer>>asList(
                new ContinuousRange<>(24000, 24000),
                new ContinuousRange<>(24000, 30000),
                new ContinuousRange<>(30000, 30000),
                new ContinuousRange<>(30000, 36000),
                new ContinuousRange<>(30000, 34000)
        );

        // When
        Range<Integer> maxRange = Collections.max(fpsRangesList, comparator);

        // Then
        Assert.assertEquals(
                maxRange,
                new ContinuousRange<>(30000, 36000)
        );
    }

    @Test
    public void findMinFpsRange() throws Exception {
        // Given
        List<Range<Integer>> fpsRangesList = Arrays.<Range<Integer>>asList(
                new ContinuousRange<>(24000, 24000),
                new ContinuousRange<>(24000, 30000),
                new ContinuousRange<>(30000, 30000),
                new ContinuousRange<>(20000, 26000),
                new ContinuousRange<>(20000, 28000)
        );

        // When
        Range<Integer> minRange = Collections.min(fpsRangesList, comparator);

        // Then
        Assert.assertEquals(
                minRange,
                new ContinuousRange<>(20000, 26000)
        );
    }
}
