package io.fotoapparat.hardware.v2;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Thread handler with looper to execute async camera operations.
 */
public class CameraThread extends HandlerThread {

    private static CameraThread INSTANCE;

    /**
     * @return the instance of this class.
     */
    public static CameraThread getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CameraThread();
        }
        return INSTANCE;
    }

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
