package io.fotoapparat.view

import android.graphics.SurfaceTexture
import android.view.SurfaceHolder
import io.fotoapparat.view.Preview.Surface
import io.fotoapparat.view.Preview.Texture

/**
 * A camera preview view.
 */
sealed class Preview {

    /**
     * A [SurfaceTexture] camera preview.
     */
    data class Texture(
            val surfaceTexture: SurfaceTexture
    ) : Preview()

    /**
     * A [Surface] camera preview.
     *
     * Wraps [SurfaceHolder].
     */
    data class Surface(
            val surfaceHolder: SurfaceHolder
    ) : Preview()
}

/**
 * Creates a new [Texture].
 */
internal fun SurfaceTexture.toPreview() = Texture(surfaceTexture = this)

/**
 * Creates a new [Surface].
 */
internal fun SurfaceHolder.toPreview() = Surface(surfaceHolder = this)