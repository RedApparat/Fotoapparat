package io.fotoapparat.hardware.display

import android.content.Context
import android.view.Surface
import android.view.WindowManager

/**
 * A phone's display.
 */
internal open class Display(context: Context) {

    private val display = context.getDisplay()

    /**
     * Returns the rotation of the screen from its "natural" orientation in degrees.
     */
    open fun getRotation(): Int = when (display.rotation) {
        Surface.ROTATION_90 -> 90
        Surface.ROTATION_180 -> 180
        Surface.ROTATION_270 -> 270
        Surface.ROTATION_0 -> 0
        else -> 0
    }

}

private fun Context.getDisplay() = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
