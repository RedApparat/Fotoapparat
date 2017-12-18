package io.fotoapparat.parameter.selector;

import java.util.Collection;

import io.fotoapparat.parameter.AntiBandingMode;

/**
 * Selector functions for {@link AntiBandingMode}.
 */
public class AntiBandingModeSelectors {

    /**
     * @return {@link SelectorFunction} which provides an auto anti banding mode if available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<AntiBandingMode>, AntiBandingMode> auto() {
        return antiBandingMode(AntiBandingMode.AUTO);
    }

    /**
     * @return {@link SelectorFunction} which provides a 50hz banding mode if available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<AntiBandingMode>, AntiBandingMode> hz50() {
        return antiBandingMode(AntiBandingMode.HZ50);
    }

    /**
     * @return {@link SelectorFunction} which provides a 60hz anti banding mode if available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<AntiBandingMode>, AntiBandingMode> hz60() {
        return antiBandingMode(AntiBandingMode.HZ60);
    }

    /**
     * @return {@link SelectorFunction} which provides a disabled anti banding mode if available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<AntiBandingMode>, AntiBandingMode> none() {
        return antiBandingMode(AntiBandingMode.NONE);
    }

    /**
     * @return function which selects given {@link AntiBandingMode} from the list if it is available. If
     * it is not available - provides {@code null}.
     */
    private static SelectorFunction<Collection<AntiBandingMode>, AntiBandingMode> antiBandingMode(final AntiBandingMode antiBandingMode) {
        return Selectors.single(antiBandingMode);
    }

}
