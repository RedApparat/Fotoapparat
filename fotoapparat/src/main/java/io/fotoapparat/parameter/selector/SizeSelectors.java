package io.fotoapparat.parameter.selector;

import java.util.Collection;
import java.util.Collections;

import io.fotoapparat.parameter.Size;
import io.fotoapparat.util.CompareSizesByArea;

/**
 * Selector functions for {@link Size}.
 */
public class SizeSelectors {

    private static final CompareSizesByArea COMPARATOR_BY_AREA = new CompareSizesByArea();

    /**
     * @return {@link SelectorFunction} which always provides the biggest size.
     */
    public static SelectorFunction<Collection<Size>, Size> biggestSize() {
        return new SelectorFunction<Collection<Size>, Size>() {
            @Override
            public Size select(Collection<Size> items) {
                if (items.isEmpty()) {
                    return null;
                }

                return Collections.max(items, COMPARATOR_BY_AREA);
            }
        };
    }

    /**
     * @return {@link SelectorFunction} which always provides the smallest size.
     */
    public static SelectorFunction<Collection<Size>, Size> smallestSize() {
        return new SelectorFunction<Collection<Size>, Size>() {
            @Override
            public Size select(Collection<Size> items) {
                if (items.isEmpty()) {
                    return null;
                }

                return Collections.min(items, COMPARATOR_BY_AREA);
            }
        };
    }

}
