@file:Suppress("DEPRECATION")

package io.fotoapparat.parameter.extract

import android.hardware.Camera

/**
 * Extracts a [List] of raw [String] values for the given [keys] from the [Camera.Parameters].
 *
 * Empty list of values will be returned
 */
internal fun Camera.Parameters.extractRawCameraValues(keys: List<String>): List<String> {
    keys.forEach {
        getValuesForKey(key = it)
                ?.let { return it }
    }

    return emptyList()
}

private fun Camera.Parameters.getValuesForKey(key: String): List<String>? {
    return get(key)?.split(regex = ",".toRegex())
}
