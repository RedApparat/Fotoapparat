package io.fotoapparat.util;

import java.util.Comparator;

import io.fotoapparat.parameter.range.Range;

/**
 * Comparator based on bounds check. Lower bound has higher priority.
 */
public class CompareFpsRangeByBounds implements Comparator<Range<Integer>> {

    @Override
    public int compare(Range<Integer> fpsRange1, Range<Integer> fpsRange2) {
        int lowerBoundCompare = fpsRange1.lowest().compareTo(fpsRange2.lowest());
        if (lowerBoundCompare == 0) {
            int upperBoundCompare = fpsRange1.highest().compareTo(fpsRange2.highest());
            return upperBoundCompare;
        }
        return lowerBoundCompare;
    }
}
