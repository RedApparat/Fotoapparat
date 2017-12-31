package io.fotoapparat.selector

/**
 * @param quality The specified Jpeq quality value
 * @return Selector function which selects the specified Jpeq quality value.
 */
fun manualJpegQuality(quality: Int): IntRange.() -> Int? = single(quality)

/**
 * @return Selector function which always provides the highest quality.
 */
fun highestQuality(): IntRange.() -> Int? = highest()

/**
 * @return Selector function which always provides the lowest quality.
 */
fun lowestQuality(): IntRange.() -> Int? = lowest()