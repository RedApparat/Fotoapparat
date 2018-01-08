package io.fotoapparat.hardware.metering

import io.fotoapparat.parameter.Resolution

/**
 * The request to focus camera at a particular point.
 */
data class FocalRequest(

        /**
         * The point where when user would like to focus.
         */
        val point: PointF,

        /**
         * Resolution of the preview
         */
        val previewResolution: Resolution
)

