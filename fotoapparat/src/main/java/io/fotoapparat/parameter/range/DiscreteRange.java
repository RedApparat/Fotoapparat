package io.fotoapparat.parameter.range;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by ychen on 5/25/2017.
 */
class DiscreteRange<T extends Comparable<? super T>> implements Range<T> {
    Collection<T> values;
    public DiscreteRange(Collection<T> values) {
        this.values = values;
    }

    @Override
    public boolean contains(T value) {
        return values.contains(value);
    }

    @Override
    public T highest() {
        if(values.isEmpty()) {
            return null;
        }
        return Collections.max(values);
    }

    @Override
    public T lowest() {
        if(values.isEmpty()) {
            return null;
        }
        return Collections.min(values);
    }
}
