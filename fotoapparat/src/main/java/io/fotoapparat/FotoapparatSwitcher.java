package io.fotoapparat;

import android.support.annotation.NonNull;

/**
 * Switches between different instances of {@link Fotoapparat}. Convenient when you want to allow
 * user to switch between different cameras or configurations.
 * <p>
 * This class is not thread safe. Consider using it from a single thread.
 */
public class FotoapparatSwitcher {

    @NonNull
    private Fotoapparat fotoapparat;

    private boolean started = false;

    private FotoapparatSwitcher(@NonNull Fotoapparat fotoapparat) {
        this.fotoapparat = fotoapparat;
    }

    /**
     * @return {@link FotoapparatSwitcher} with given {@link Fotoapparat} used by default.
     */
    public static FotoapparatSwitcher withDefault(@NonNull Fotoapparat fotoapparat) {
        return new FotoapparatSwitcher(fotoapparat);
    }

    /**
     * Starts {@link Fotoapparat} associated with this switcher. Every new {@link Fotoapparat} will
     * be started automatically until {@link #stop()} is called.
     *
     * @throws IllegalStateException if switcher is already started.
     */
    public void start() {
        fotoapparat.start();

        started = true;
    }

    /**
     * Stops currently used {@link Fotoapparat}.
     *
     * @throws IllegalStateException if switcher is already stopped.
     */
    public void stop() {
        fotoapparat.stop();

        started = false;
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
        if (started) {
            this.fotoapparat.stop();
            fotoapparat.start();
        }

        this.fotoapparat = fotoapparat;
    }

    /**
     * @return currently used instance of {@link Fotoapparat}.
     */
    @NonNull
    public Fotoapparat getCurrentFotoapparat() {
        return fotoapparat;
    }

}
