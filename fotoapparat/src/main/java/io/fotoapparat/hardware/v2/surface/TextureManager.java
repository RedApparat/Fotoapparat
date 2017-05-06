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
                parametersProvider.getPreviewSize(),
                screenOrientation
        ).call();
    }

    /**
     * Returns the {@link Surface} when it becomes available.
     *
     * @return the surface of the view.
     */
    public Surface getSurface() {
        if (surface == null) {
            throw new IllegalStateException("Surface not yet available.");
        }
        return surface;
    }

}
