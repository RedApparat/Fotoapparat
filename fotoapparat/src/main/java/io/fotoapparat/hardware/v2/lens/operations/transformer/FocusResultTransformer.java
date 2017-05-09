package io.fotoapparat.hardware.v2.lens.operations.transformer;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.lens.FocusResult;
import io.fotoapparat.result.transformer.Transformer;

/**
 * Transforms a {@link CaptureResult} into a {@link FocusResult}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FocusResultTransformer implements Transformer<CaptureResult, FocusResult> {

    @Override
    public FocusResult transform(CaptureResult input) {
        Integer autoFocusState = input.get(CaptureResult.CONTROL_AF_STATE);

        boolean lockSucceeded = autoFocusState != null && isFocusLocked(autoFocusState);
        boolean needsExposureMeasurement = needsExposureMeasurement(input);

        return new FocusResult(lockSucceeded, needsExposureMeasurement);
    }

    private boolean needsExposureMeasurement(CaptureResult input) {
        Integer autoExposure = input.get(CaptureResult.CONTROL_AE_STATE);
        return autoExposure == null || !isExposureValuesConverged(autoExposure);
    }

    private boolean isExposureValuesConverged(Integer autoExposure) {
        return autoExposure == CaptureResult.CONTROL_AE_STATE_CONVERGED;
    }

    private boolean isFocusLocked(Integer autoFocusState) {
        return autoFocusState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED
                || autoFocusState == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED;
    }
}
