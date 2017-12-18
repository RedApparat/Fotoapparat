package io.fotoapparat.selector


/**
 * @param iso The specified ISO value
 * @return Selector function which selects the specified ISO value. If there
 * is no specified value - selects default or previous ISO value.
 */
fun manualSensorSensitivity(iso: Int): Iterable<Int>.() -> Int? = single(iso)

/**
 * @return Selector function which selects highest ISO value.
 */
fun highestSensorSensitivity(): Iterable<Int>.() -> Int? = highest()

/**
 * @return Selector function which selects lowest ISO value.
 */
fun lowestSensorSensitivity(): Iterable<Int>.() -> Int? = lowest()