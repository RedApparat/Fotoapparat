package io.fotoapparat.error;

import android.os.Handler;
import android.os.Looper;

import io.fotoapparat.hardware.CameraException;

/**
 * Factory methods for callbacks.
 */
public class Callbacks {

    private static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    /**
     * @return CameraErrorCallback which will always move execution to the main thread.
     */
    public static CameraErrorCallback onMainThread(final CameraErrorCallback original) {
        return new CameraErrorCallback() {
            @Override
            public void onError(final CameraException e) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    original.onError(e);
                } else {
                    MAIN_THREAD_HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            original.onError(e);
                        }
                    });
                }
            }
        };
    }

}
