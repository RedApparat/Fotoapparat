package io.fotoapparat.view

import android.graphics.SurfaceTexture
import android.view.TextureView

/**
 * [TextureView.SurfaceTextureListener] called when a [SurfaceTexture] becomes available.
 */
internal class TextureAvailabilityListener(
        private val onSurfaceTextureAvailable: SurfaceTexture.() -> Unit
) : TextureView.SurfaceTextureListener {

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        onSurfaceTextureAvailable(surface)
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        // Do nothing
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        // Do nothing
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        // Do nothing
    }
}