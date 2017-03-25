package io.fotoapparat.hardware.v2;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Thread handler with looper to execute async camera operations.
 */
public class CameraThread extends HandlerThread {

	private static CameraThread INSTANCE;

	private Handler handler;

	/**
	 * @return the instance of this class.
	 */
	public static CameraThread getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CameraThread();
		}
		return INSTANCE;
	}

	private CameraThread() {
		super("CameraThread");
		start();
	}

	/**
	 * @return the handler for this thread.
	 */
	public Handler getHandler() {
		if (handler == null) {
			handler = new Handler(getLooper());
		}
		return handler;
	}
}
