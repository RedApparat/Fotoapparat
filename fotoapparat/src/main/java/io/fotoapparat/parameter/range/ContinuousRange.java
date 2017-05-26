package io.fotoapparat.parameter.range;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by ychen on 5/25/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ContinuousRange<T extends Comparable<? super T>> extends Range<T> {
    android.util.Range<T> range;

    public ContinuousRange(android.util.Range<T> range) {
        this.range = range;
    }

    @Override
    public boolean contains(T value) {
        return range.contains(value);
    }

    @Override
    public T highest() {
        return range.getUpper();
    }

    @Override
    public T lowest() {
        return range.getLower();
    }
}
