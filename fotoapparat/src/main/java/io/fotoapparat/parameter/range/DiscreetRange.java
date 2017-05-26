package io.fotoapparat.parameter.range;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by ychen on 5/25/2017.
 */

public class DiscreetRange<T extends Comparable<? super T>> extends Range<T> {
    Collection<T> values;
    public DiscreetRange(Collection<T> values) {
        this.values = values;
    }

    @Override
    public boolean contains(T value) {
        return values.contains(value);
    }

    @Override
    public T highest() {
        return Collections.max(values);
    }

    @Override
    public T lowest() {
        return Collections.min(values);
    }
}
