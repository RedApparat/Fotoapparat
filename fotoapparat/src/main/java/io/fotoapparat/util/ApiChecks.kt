package io.fotoapparat.util

import android.os.Build

/**
 * Checks if the current build is at least [Build.VERSION_CODES.LOLLIPOP] api.
 */
internal fun isAboveLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
