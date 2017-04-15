package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * A {@link CaptureCallback} which will provide the resulting {@link Stage} after a auto focus
 * request has been performed.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class LockFocusCallback extends StageCallback {

	@Override
	public Stage processResult(CaptureResult result) {
		Integer autoFocusState = result.get(CaptureResult.CONTROL_AF_STATE);

		if (autoFocusState != null && autoFocusState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED) {

			Integer autoExposure = result.get(CaptureResult.CONTROL_AE_STATE);
			if (autoExposure != null && autoExposure == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
				return Stage.CAPTURE;
			} else {
				return Stage.PRECAPTURE;
			}

		} else {
			return Stage.UNFOCUSED;
		}
	}
}
