package io.fotoapparat.parameter.range;

/**
 * Created by ychen on 5/25/2017.
 */

public abstract class Range<T> {
    abstract public boolean contains(T value);
    abstract public T highest();
    abstract public T lowest();

    public String toString() {
        return String.format("%1$s to %2$s", lowest(), highest());
    }

    public static <T> Range<T> emptyRange() {
         return new EmptyRange<>();
    }

    public static class EmptyRange<T> extends Range<T> {
        @Override
        public boolean contains(T value) {
            return false;
        }

        @Override
        public T highest() {
            return null;
        }

        @Override
        public T lowest() {
            return null;
        }
    }
}
