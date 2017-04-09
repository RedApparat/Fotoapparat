package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class PrecaptureCallback extends StageCallback {


	@SuppressWarnings("ConstantConditions")
	@Override
	void process(CaptureResult result) {

//		int autoExposure = result.get(CaptureResult.CONTROL_AE_STATE);
//		if (autoExposure != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
			setStage(Stage.CAPTURE);
//		} else {
//			setStage(Stage.PRECAPTURE);
//		}
	}


}
