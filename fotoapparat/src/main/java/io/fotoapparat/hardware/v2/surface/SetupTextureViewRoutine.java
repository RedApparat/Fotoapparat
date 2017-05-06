package io.fotoapparat.hardware.v2.surface;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;

import java.util.concurrent.Callable;

import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.view.TextureSizeChangeListener;
import io.fotoapparat.view.TextureSizeChangeListener.Listener;

/**
 * Setups the {@link TextureView} and returns its {@link Surface}.
 */
class SetupTextureViewRoutine implements Callable<Surface> {

    private final TextureView textureView;
    private final ParametersProvider parametersProvider;
    private final int screenOrientation;

    SetupTextureViewRoutine(TextureView textureView,
                            ParametersProvider parametersProvider,
                            int screenOrientation) {
        this.textureView = textureView;
        this.parametersProvider = parametersProvider;
        this.screenOrientation = screenOrientation;
    }

    @Override
    public Surface call() {

        SurfaceTexture surfaceTexture = new GetSurfaceTextureTask(
                textureView
        ).call();

        new SetTextureBufferSizeTask(
                surfaceTexture,
                parametersProvider.getPreviewSize()
        ).run();

        new CorrectTextureOrientationTask(
                textureView,
                screenOrientation
        ).run();

        textureView.setSurfaceTextureListener(
                new TextureSizeChangeListener(new Listener() {
                    @Override
                    public void onTextureSizeChanged(int width, int height) {
                        new CorrectTextureOrientationTask(
                                textureView,
                                screenOrientation,
                                width,
                                height
                        ).run();
                    }
                }));

        return new Surface(surfaceTexture);
    }

}
