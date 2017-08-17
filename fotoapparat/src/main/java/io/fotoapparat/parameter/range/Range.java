package io.fotoapparat.parameter.range;

/**
 * Interface for representing ranges of arbitrary object.
 *
 * @param <T> type of elements in that range.
 */
public interface Range<T> {

    /**
     * Returns highest value in this range.
     *
     * @return highest value in this range.
     */
    T highest();

    /**
     * Returns lowest value in this range.
     *
     * @return lowest value in this range.
     */
    T lowest();

    /**
     * Returns <tt>true</tt> if this range contains the specified object,
     * otherwise <tt>false</tt>.
     *
     * @param value the specified object
     * @return <tt>true</tt> if this range contains the specified object
     */
    boolean contains(T value);
}
