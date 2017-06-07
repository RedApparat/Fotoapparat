package io.fotoapparat.parameter.selector;

import java.util.Collection;

import io.fotoapparat.parameter.Flash;

/**
 * Selector functions for {@link io.fotoapparat.parameter.Flash}.
 */
public class FlashSelectors {

    /**
     * @return {@link SelectorFunction} which provides a disabled flash firing mode if available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<Flash>, Flash> off() {
        return flash(Flash.OFF);
    }

    /**
     * @return {@link SelectorFunction} which provides a forced on flash firing mode if available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<Flash>, Flash> on() {
        return flash(Flash.ON);
    }

    /**
     * @return {@link SelectorFunction} which provides an auto flash firing mode if available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<Flash>, Flash> autoFlash() {
        return flash(Flash.AUTO);
    }

    /**
     * @return {@link SelectorFunction} which provides an auto flash firing mode with red eye
     * reduction if available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<Flash>, Flash> autoRedEye() {
        return flash(Flash.AUTO_RED_EYE);
    }

    /**
     * @return {@link SelectorFunction} which provides a torch (continuous on) flash firing mode if
     * available.
     * Otherwise provides {@code null}.
     */
    public static SelectorFunction<Collection<Flash>, Flash> torch() {
        return flash(Flash.TORCH);
    }

    /**
     * @return function which selects given {@link Flash} from the list if it is available. If it is
     * not available or flash unit is not available - provides {@code null}.
     */
    private static SelectorFunction<Collection<Flash>, Flash> flash(final Flash flash) {
        return Selectors.single(flash);
    }

}
