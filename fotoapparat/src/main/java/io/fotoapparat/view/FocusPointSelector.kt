package io.fotoapparat.view

import io.fotoapparat.hardware.metering.FocalRequest

/**
 * Selects the a point where the camera should focus at.
 */
interface FocalPointSelector {

    /**
     * Sets a listener to be called when a [FocalRequest] has been selected.
     */
    fun setFocalPointListener(listener: (FocalRequest) -> Unit)

}