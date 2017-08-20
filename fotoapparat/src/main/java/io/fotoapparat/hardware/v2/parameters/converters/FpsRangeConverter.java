package io.fotoapparat.hardware.v2.parameters.converters;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.parameter.range.ContinuousRange;
import io.fotoapparat.parameter.range.Range;

/**
 * Facilitates interaction between Android native {@link android.util.Range}
 * and {@link io.fotoapparat.Fotoapparat}'s {@link Range}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FpsRangeConverter {

    /**
     * Converts an Android native {@link android.util.Range} to
     * {@link io.fotoapparat.Fotoapparat}'s {@link Range}.
     *
     * @param nativeRange The native Android {@link android.util.Range} value.
     * @return The {@link io.fotoapparat.Fotoapparat}'s {@link Range} value.
     */
    public static <T extends Number & Comparable<T>> Range<T> toFotoapparatRange(android.util.Range<T> nativeRange) {
        return new ContinuousRange<T>(nativeRange.getLower(), nativeRange.getUpper());
    }

    /**
     * Converts {@link io.fotoapparat.Fotoapparat}'s {@link Range} to Android native
     * {@link android.util.Range}.
     *
     * @param fotoapparatRange The {@link io.fotoapparat.Fotoapparat}'s {@link Range} value.
     * @return The native Android {@link android.util.Range} value.
     */
    public static <T extends Comparable<T>> android.util.Range<T> toNativeRange(Range<T> fotoapparatRange) {
        return new android.util.Range<>(fotoapparatRange.lowest(), fotoapparatRange.highest());
    }
}
