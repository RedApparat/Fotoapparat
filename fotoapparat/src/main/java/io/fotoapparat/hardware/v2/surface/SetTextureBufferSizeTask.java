package io.fotoapparat.hardware.v2.surface;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.os.Build;

import io.fotoapparat.parameter.Size;

/**
 * Sets the preview {@link Size} on a {@link SurfaceTexture}.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class SetTextureBufferSizeTask implements Runnable {

    private final SurfaceTexture surfaceTexture;
    private final Size previewSize;

    SetTextureBufferSizeTask(SurfaceTexture surfaceTexture, Size previewSize) {
        this.surfaceTexture = surfaceTexture;
        this.previewSize = previewSize;
    }

    @Override
    public void run() {
        surfaceTexture.setDefaultBufferSize(previewSize.width, previewSize.height);
    }

}
