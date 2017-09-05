package io.fotoapparat.util;

import java.util.Iterator;

/**
 * String related utils.
 */
public class StringUtils {

    /**
     * Returns a string containing the values joined by delimiters.
     */
    public static String join(CharSequence delimiter, Iterable values) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = values.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(delimiter);
                sb.append(it.next());
            }
        }
        return sb.toString();
    }
}
