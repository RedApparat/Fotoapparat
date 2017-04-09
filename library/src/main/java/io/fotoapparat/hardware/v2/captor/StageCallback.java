package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
abstract class StageCallback extends CameraCaptureSession.CaptureCallback {

	private CountDownLatch countDownLatch = new CountDownLatch(1);
	private Stage stage;

	@Override
	public void onCaptureProgressed(@NonNull CameraCaptureSession session,
									@NonNull CaptureRequest request,
									@NonNull CaptureResult partialResult) {
		super.onCaptureProgressed(session, request, partialResult);
//		process(partialResult);
	}

	@Override
	public void onCaptureCompleted(@NonNull CameraCaptureSession session,
								   @NonNull CaptureRequest request,
								   @NonNull TotalCaptureResult result) {
		super.onCaptureCompleted(session, request, result);
		process(result);
	}


	void setStage(Stage stage) {
		Log.wtf("StageCallback", "setStage " + stage);
		if(stage == Stage.PRECAPTURE){
			Log.wtf("StageCallback", "setStage --------------------------------------");
		}
		this.stage = stage;
		countDownLatch.countDown();
	}

	Stage onStageAcquired() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// do nothing
		}
		return stage;
	}

	abstract void process(CaptureResult result);
}
