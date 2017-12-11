package io.fotoapparat.exception

/**
 * Thrown when zoom level is outside of [0..1] range.
 */
class LevelOutOfRangeException(zoomLevel: Float) : RuntimeException(zoomLevel.toString() + " is out of range [0..1]")