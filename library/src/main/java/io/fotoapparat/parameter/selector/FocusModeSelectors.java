package io.fotoapparat.parameter.selector;

import java.util.Collection;

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
        return new SelectorFunction<FocusMode>() {
            @Override
            public FocusMode select(Collection<FocusMode> items) {
                return items.contains(focusMode)
                        ? focusMode
                        : null;
            }
        };
    }

}
