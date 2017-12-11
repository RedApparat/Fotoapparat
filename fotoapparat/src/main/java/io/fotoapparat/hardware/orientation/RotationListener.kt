package io.fotoapparat.hardware.orientation

import android.content.Context
import android.view.OrientationEventListener


/**
 * Wrapper around [OrientationEventListener] to notify when the device's rotation has changed.
 */
open internal class RotationListener(
        context: Context
) : OrientationEventListener(context) {

    lateinit var orientationChanged: () -> Unit

    override fun onOrientationChanged(orientation: Int) {
        if (canDetectOrientation()) {
            orientationChanged()
        }
    }

}