package io.fotoapparat.hardware.v2.parameters.converters;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.parameter.range.ContinuousRange;
import io.fotoapparat.parameter.range.Range;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FpsRangeConverter {

    public static Range<Integer> toFotoapparatRange(android.util.Range<Integer> nativeRange) {
        return new ContinuousRange<>(nativeRange);
    }

    public static android.util.Range<Integer> toNativeRange(Range<Integer> fotoapparatRange) {
        return new android.util.Range<>(fotoapparatRange.lowest(), fotoapparatRange.highest());
    }
}
