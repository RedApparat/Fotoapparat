package io.fotoapparat.selector

typealias SensorSensitivitySelector = Iterable<Int>.() -> Int?

/**
 * @param iso The specified ISO value
 * @return Selector function which selects the specified ISO value. If there
 * is no specified value - selects default or previous ISO value.
 */
fun manualSensorSensitivity(iso: Int): SensorSensitivitySelector = single(iso)

/**
 * @return Selector function which selects highest ISO value.
 */
fun highestSensorSensitivity(): SensorSensitivitySelector = highest()

/**
 * @return Selector function which selects lowest ISO value.
 */
fun lowestSensorSensitivity(): SensorSensitivitySelector = lowest()
