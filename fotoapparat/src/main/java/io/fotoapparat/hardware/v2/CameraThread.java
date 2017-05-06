package io.fotoapparat.hardware.v2;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Thread handler with looper to execute async camera2 operations.
 */
public class CameraThread extends HandlerThread {

    public CameraThread() {
        super("CameraThread");
        start();
    }

    /**
     * Creates a new handler for the this thread.
     *
     * @return the new handler for this thread.
     */
    public Handler createHandler() {
        return new Handler(getLooper());
    }
}
