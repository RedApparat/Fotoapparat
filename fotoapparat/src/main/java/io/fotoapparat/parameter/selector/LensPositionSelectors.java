package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.LensPosition;

/**
 * Selector functions for {@link LensPosition}.
 */
public class LensPositionSelectors {

    /**
     * @return {@link SelectorFunction} which provides the front camera if it is available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<LensPosition> front() {
        return lensPosition(LensPosition.FRONT);
    }

    /**
     * @return {@link SelectorFunction} which provides the back camera if it is available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<LensPosition> back() {
        return lensPosition(LensPosition.BACK);
    }

    /**
     * @return {@link SelectorFunction} which provides the external camera if it is available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<LensPosition> external() {
        return lensPosition(LensPosition.EXTERNAL);
    }

    private static SelectorFunction<LensPosition> lensPosition(final LensPosition position) {
        return Selectors.single(position);
    }

}
