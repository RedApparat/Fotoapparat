package io.fotoapparat.hardware.v2;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

/**
 * A callback triggered when a photo capture has been completed.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class CapturePhotoCallback extends CameraCaptureSession.CaptureCallback {

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private TotalCaptureResult result;

	@Override
	public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
		super.onCaptureCompleted(session, request, result);

		this.result = result;
		countDownLatch.countDown();
	}

	/**
	 * Waits and returns a capture result upon completion.
	 *
	 * @return a {@link TotalCaptureResult} when the capture has been completed.
	 */
	public TotalCaptureResult getResult() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// do nothing
		}
		return result;
	}
}
