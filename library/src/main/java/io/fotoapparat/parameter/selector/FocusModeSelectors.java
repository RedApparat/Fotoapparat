package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.FocusMode;

/**
 * Selector functions for {@link FocusMode}.
 */
public class FocusModeSelectors {

    /**
     * @return function which selects given {@link FocusMode} from the list if it is available. If
     * it is not available - provides {@code null}.
     */
    public static SelectorFunction<FocusMode> focusMode(final FocusMode focusMode) {
        return Selectors.single(focusMode);
    }

}
