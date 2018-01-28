package io.fotoapparat.selector

typealias ExposureSelector = IntRange.() -> Int?

/**
 * @param exposure The specified exposure compensation value
 * @return Selector function which selects the specified exposure compensation value.
 */
fun manualExposure(exposure: Int): ExposureSelector = single(exposure)

/**
 * @return Selector function which always provides the highest exposure.
 */
fun highestExposure(): ExposureSelector = highest()

/**
 * @return Selector function which always provides the lowest exposure.
 */
fun lowestExposure(): ExposureSelector = lowest()

/**
 * @return Selector function which always provides the default exposure.
 */
fun defaultExposure(): ExposureSelector = single(0)