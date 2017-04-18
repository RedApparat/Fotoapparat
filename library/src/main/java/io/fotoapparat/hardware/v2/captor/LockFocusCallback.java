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

		if (autoFocusState != null && isFocusLocked(autoFocusState)) {

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

	private boolean isFocusLocked(Integer autoFocusState) {
		return autoFocusState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED
				|| autoFocusState == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED;
	}
}
