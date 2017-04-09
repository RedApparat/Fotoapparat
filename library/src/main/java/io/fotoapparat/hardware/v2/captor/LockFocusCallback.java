package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class LockFocusCallback extends StageCallback {


	@Override
	void process(CaptureResult result) {
		Integer autoFocusState = result.get(CaptureResult.CONTROL_AF_STATE);

		if (autoFocusState != null && autoFocusState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED) {

			Integer autoExposure = result.get(CaptureResult.CONTROL_AE_STATE);
			if (autoExposure != null && autoExposure == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
				setStage(Stage.CAPTURE);
			} else {
				setStage(Stage.PRECAPTURE);
			}

		} else {
			setStage(Stage.UNFOCUSED);
		}
	}


}
