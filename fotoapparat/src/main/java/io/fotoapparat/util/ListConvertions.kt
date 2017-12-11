package io.fotoapparat.util

/**
 * Converts a [List] of [String] to a [List] of [Int], if possible.
 * If no value can be converted to [Int], empty list will be returned.
 */
internal fun List<String>.toInts() = mapNotNull {
    try {
        it.trim().toInt()
    } catch (e: NumberFormatException) {
        // Found not number option. Skip it.
        null
    }
}
