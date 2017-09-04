package io.fotoapparat.hardware.v2.parameters.converters;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.range.Ranges;

/**
 * Facilitates interaction between Android native {@link android.util.Range}
 * and {@link io.fotoapparat.Fotoapparat}'s {@link Range}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RangeConverter {

    /**
     * Converts an Android native {@link android.util.Range} to
     * {@link io.fotoapparat.Fotoapparat}'s {@link Range}.
     *
     * @param nativeRange The native Android {@link android.util.Range} value.
     * @return The {@link io.fotoapparat.Fotoapparat}'s {@link Range} value.
     */
    @NonNull
    public static <T extends Number & Comparable<T>> Range<T> toFotoapparatRange(@Nullable android.util.Range<T> nativeRange) {
        if (nativeRange != null) {
            return Ranges.continuousRange(nativeRange.getLower(), nativeRange.getUpper());
        } else  {
            return Ranges.emptyRange();
        }
    }

    /**
     * Converts {@link io.fotoapparat.Fotoapparat}'s {@link Range} to Android native
     * {@link android.util.Range}.
     *
     * @param fotoapparatRange The {@link io.fotoapparat.Fotoapparat}'s {@link Range} value.
     * @return The native Android {@link android.util.Range} value.
     */
    @Nullable
    public static <T extends Comparable<T>> android.util.Range<T> toNativeRange(@NonNull Range<T> fotoapparatRange) {
        if (Ranges.isEmpty(fotoapparatRange)) {
            return null;
        } else {
            return new android.util.Range<>(fotoapparatRange.lowest(), fotoapparatRange.highest());
        }
    }
}
