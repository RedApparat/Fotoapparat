package io.fotoapparat.util

/**
 * System line separator.
 */
internal val lineSeparator = System.getProperty("line.separator")

/**
 * Prints a [Set] in logcat in a structured way.
 */
internal fun Set<Any>.wrap(): String = "${this.map { lineSeparator + "\t\t" + it }}" + lineSeparator

/**
 * Prints a item in logcat with left margin and appends a line separator
 */
internal fun Any?.wrap(): String = (this ?: "null").let { "\t\t" + it + lineSeparator }
