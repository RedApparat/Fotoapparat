package io.fotoapparat.result.transformer

import io.fotoapparat.parameter.Resolution


/**
 * @return Transforming function which always returns the same size as it receives.
 */
fun originalResolution(): (Resolution) -> Resolution = {
    it
}

/**
 * @param scaleFactor scale factor which would be applied to image.
 * @return Transforming function which returns size scaled by given factor.
 */
fun scaled(scaleFactor: Float): (Resolution) -> Resolution = { input ->
    Resolution(
            width = (input.width * scaleFactor).toInt(),
            height = (input.height * scaleFactor).toInt()
    )
}