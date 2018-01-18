package io.fotoapparat.hardware.display

import android.content.Context
import android.view.Surface
import android.view.WindowManager
import io.fotoapparat.hardware.orientation.Orientation
import io.fotoapparat.hardware.orientation.Orientation.Horizontal.Landscape
import io.fotoapparat.hardware.orientation.Orientation.Horizontal.ReverseLandscape
import io.fotoapparat.hardware.orientation.Orientation.Vertical.Portrait
import io.fotoapparat.hardware.orientation.Orientation.Vertical.ReversePortrait

/**
 * A phone's display.
 */
internal open class Display(context: Context) {

    private val display = context.getDisplay()

    /**
     * Returns the orientation of the screen.
     */
    open fun getOrientation(): Orientation = when (display.rotation) {
        Surface.ROTATION_0 -> Portrait
        Surface.ROTATION_90 -> Landscape
        Surface.ROTATION_180 -> ReversePortrait
        Surface.ROTATION_270 -> ReverseLandscape
        else -> Portrait
    }

}

private fun Context.getDisplay() = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
