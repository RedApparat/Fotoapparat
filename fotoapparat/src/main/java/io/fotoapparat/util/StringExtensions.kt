package io.fotoapparat.util


/**
 * System line separator.
 */
internal val lineSeparator = System.getProperty("line.separator")

// TODO
internal fun Set<Any>.wrap(): String = "${this.map { lineSeparator + "\t\t" + it }}" + lineSeparator

// TODO
internal fun Any.wrap() = "\t\t" + this + lineSeparator
