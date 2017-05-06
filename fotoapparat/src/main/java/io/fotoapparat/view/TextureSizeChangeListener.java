package io.fotoapparat.view;

import android.graphics.SurfaceTexture;
import android.view.TextureView;

/**
 * This listener can be used to be notified when the {@link SurfaceTexture}
 * has a change in its size.
 */
public class TextureSizeChangeListener implements TextureView.SurfaceTextureListener {

    private Listener listener;

    public TextureSizeChangeListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        // Do nothing
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (listener != null) {
            listener.onTextureSizeChanged(width, height);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (listener != null) {
            listener.onTextureSizeChanged(0, 0);
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Do nothing
    }

    /**
     * Listener to be used when the {@link SurfaceTexture}
     * has a change in its size.
     */
    public interface Listener {

        /**
         * Invoked when the {@link SurfaceTexture}'s size has changed.
         *
         * @param width  The new width of the surface
         * @param height The new height of the surface
         */
        void onTextureSizeChanged(int width, int height);
    }
}
