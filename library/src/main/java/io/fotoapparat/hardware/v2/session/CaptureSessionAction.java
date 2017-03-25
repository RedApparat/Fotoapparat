package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraCaptureSession;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

/**
 * A wrapper around {@link CameraCaptureSession.StateCallback} to provide the opened
 * session synchronously.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class CaptureSessionAction extends CameraCaptureSession.StateCallback {

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private CameraCaptureSession session;

	@Override
	public void onConfigured(@NonNull CameraCaptureSession session) {
		this.session = session;
		countDownLatch.countDown();
	}

	@Override
	public void onConfigureFailed(@NonNull CameraCaptureSession session) {
		session.close();
	}

	/**
	 * Waits and returns the {@link CameraCaptureSession} synchronously after it has been
	 * obtained.
	 *
	 * @return the requested {@link CameraCaptureSession} to open
	 */
	CameraCaptureSession getSession() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// do nothing
		}
		return session;
	}
}
