package io.fotoapparat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Switches between different instances of {@link Fotoapparat}. Convenient when you want to allow
 * user to switch between different cameras or configurations.
 * <p>
 * This class is not thread safe. Consider using it from a single thread.
 */
public class FotoapparatSwitcher {

    /**
     * Starts {@link Fotoapparat} associated with this switcher. Every new {@link Fotoapparat} will
     * be started automatically until {@link #stop()} is called.
     *
     * @throws IllegalStateException if switcher is already started.
     */
    public void start() {
        // TODO
    }

    /**
     * Stops currently used {@link Fotoapparat}.
     *
     * @throws IllegalStateException if switcher is already stopped.
     */
    public void stop() {
        // TODO
    }

    /**
     * Switches to another {@link Fotoapparat}. If switcher is already started then previously used
     * {@link Fotoapparat} will be stopped automatically and new {@link Fotoapparat} will be
     * started.
     *
     * @param fotoapparat new {@link Fotoapparat} to use.
     * @throws NullPointerException if given {@link Fotoapparat} is {@code null}.
     */
    public void switchTo(@NonNull Fotoapparat fotoapparat) {
        // TODO
    }

    /**
     * @return currently used instance of {@link Fotoapparat} or {@code null} if switcher was not
     * started (or is stopped).
     */
    @Nullable
    public Fotoapparat getCurrentFotoapparat() {
        return null;    // TODO
    }

}
