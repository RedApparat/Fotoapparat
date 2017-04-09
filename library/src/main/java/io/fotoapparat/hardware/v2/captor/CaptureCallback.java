package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.v2.session.PreviewSession;
import io.fotoapparat.hardware.v2.session.Session;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class CaptureCallback extends StageCallback {


	private final Session session;

	public CaptureCallback(Session session) {
		this.session = session;
	}

	@Override
	public void onCaptureCompleted(@NonNull CameraCaptureSession session,
								   @NonNull CaptureRequest request,
								   @NonNull TotalCaptureResult result) {
		super.onCaptureCompleted(session, request, result);
		if(this.session instanceof PreviewSession){
			((PreviewSession) this.session).startPreview();
		}
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	void process(CaptureResult result) {
		setStage(Stage.CAPTURE_COMPLETED);
	}

}
