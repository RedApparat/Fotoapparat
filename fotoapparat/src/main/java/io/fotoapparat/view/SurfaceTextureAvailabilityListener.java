package io.fotoapparat.view;

import android.graphics.SurfaceTexture;
import android.view.TextureView;

/**
 * This listener can be used to be notified when the {@link SurfaceTexture}
 * has a change in its state.
 */
public class SurfaceTextureAvailabilityListener implements TextureView.SurfaceTextureListener {

    private Listener listener;

    public SurfaceTextureAvailabilityListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (listener != null) {
            listener.onSurfaceAvailable(surface);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Do nothing
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // Do nothing
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Do nothing
    }

    /**
     * Listener to be used when the {@link SurfaceTexture}
     * has a change in its availability.
     */
    public interface Listener {

        /**
         * Invoked when a {@link TextureView}'s SurfaceTexture is ready for use.
         *
         * @param surface The surface returned by
         *                {@link android.view.TextureView#getSurfaceTexture()}
         */
        void onSurfaceAvailable(SurfaceTexture surface);
    }
}
