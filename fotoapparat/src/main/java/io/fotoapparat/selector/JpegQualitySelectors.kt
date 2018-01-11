package io.fotoapparat.selector

typealias QualitySelector = IntRange.() -> Int?

/**
 * @param quality The specified Jpeq quality value
 * @return Selector function which selects the specified Jpeq quality value.
 */
fun manualJpegQuality(quality: Int): QualitySelector = single(quality)

/**
 * @return Selector function which always provides the highest quality.
 */
fun highestQuality(): QualitySelector = highest()

/**
 * @return Selector function which always provides the lowest quality.
 */
fun lowestQuality(): QualitySelector = lowest()