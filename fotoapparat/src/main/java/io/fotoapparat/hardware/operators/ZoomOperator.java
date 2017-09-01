package io.fotoapparat.hardware.operators;

import android.support.annotation.FloatRange;

/**
 * Modifies zoom level of the camera.
 */
public interface ZoomOperator {

    /**
     * Changes zoom level of the camera. Must be called only if zoom is supported.
     *
     * @param level normalized zoom level. Value in range [0..1].
     */
    void setZoom(@FloatRange(from = 0f, to = 1f) float level);

}
