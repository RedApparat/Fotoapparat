package io.fotoapparat.hardware.v2.surface;

import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;
import android.view.TextureView;

import io.fotoapparat.hardware.operators.SurfaceOperator;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;

/**
 * Manages the {@link SurfaceTexture} of a {@link TextureView}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TextureManager
        implements OrientationManager.Listener, SurfaceOperator {

    private final ParametersProvider parametersProvider;
    private Surface surface;
    private int screenOrientation;
    private TextureView textureView;
    private Listener listener;

    public TextureManager(OrientationManager orientationManager,
                          ParametersProvider parametersProvider) {
        this.parametersProvider = parametersProvider;
        orientationManager.addListener(this);
    }

    @Override
    public void onDisplayOrientationChanged(int orientation) {
        screenOrientation = orientation;

        if (textureView == null) {
            return;
        }

        new CorrectTextureOrientationTask(
                textureView,
                screenOrientation
        ).run();
    }

    @Override
    public void setDisplaySurface(Object displaySurface) {
        if (!(displaySurface instanceof TextureView)) {
            throw new IllegalArgumentException("Unsupported display surface: " + displaySurface);
        }

        textureView = (TextureView) displaySurface;

        surface = new SetupTextureViewRoutine(
                textureView,
                parametersProvider,
                screenOrientation
        ).call();

        if (listener != null) {
            listener.onSurfaceAvailable(surface);
        }
    }

    /**
     * Returns the {@link Surface} when it becomes available.
     *
     * @return the surface of the view.
     */
    public Surface getSurface() {
        if (surface == null) {
            throw new IllegalStateException("Target display surface has not been set.");
        }
        return surface;
    }

    /**
     * Sets a listener to be notified when a new {@link Surface} is created.
     *
     * @param listener the listener to be notified
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Notifies that a new {@link Surface} for the given display surface ({@link TextureView}) has
     * been created.
     **/
    public interface Listener {

        /**
         * Called when a new {@link Surface} for the given display surface ({@link TextureView}) has
         * been created.
         *
         * @param surface The surface of the {@link TextureView}.
         */
        void onSurfaceAvailable(Surface surface);
    }

}
