package io.fotoapparat.util;

import java.util.Comparator;

import io.fotoapparat.parameter.Size;

/**
 * Comparator based on area of the given {@link Size} objects.
 */
public class CompareSizesByArea implements Comparator<Size> {

    @Override
    public int compare(Size lhs, Size rhs) {
        return Long.signum((long) lhs.width * lhs.height -
                (long) rhs.width * rhs.height);
    }
}
