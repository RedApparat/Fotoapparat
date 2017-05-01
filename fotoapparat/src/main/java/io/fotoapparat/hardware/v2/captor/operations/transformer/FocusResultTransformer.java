package io.fotoapparat.hardware.v2.captor.operations.transformer;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.lens.FocusResultState;
import io.fotoapparat.result.transformer.Transformer;

/**
 * Transforms a {@link CaptureResult} into a {@link FocusResultState}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FocusResultTransformer implements Transformer<CaptureResult, FocusResultState> {

    @Override
    public FocusResultState transform(CaptureResult input) {
        Integer autoFocusState = input.get(CaptureResult.CONTROL_AF_STATE);

        if (autoFocusState != null && isFocusLocked(autoFocusState)) {

            Integer autoExposure = input.get(CaptureResult.CONTROL_AE_STATE);
            if (autoExposure != null && isExposureValuesConverged(autoExposure)) {
                return FocusResultState.SUCCESS;
            }

            return FocusResultState.SUCCESS_NEEDS_EXPOSURE_MEASUREMENT;
        }

        return FocusResultState.FAILURE;
    }

    private boolean isExposureValuesConverged(Integer autoExposure) {
        return autoExposure == CaptureResult.CONTROL_AE_STATE_CONVERGED;
    }

    private boolean isFocusLocked(Integer autoFocusState) {
        return autoFocusState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED
                || autoFocusState == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED;
    }
}
