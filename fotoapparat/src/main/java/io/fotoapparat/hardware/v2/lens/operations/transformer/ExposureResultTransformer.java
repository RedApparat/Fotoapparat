package io.fotoapparat.hardware.v2.lens.operations.transformer;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.lens.ExposureResultState;
import io.fotoapparat.result.transformer.Transformer;

/**
 * Transforms a {@link CaptureResult} into a {@link ExposureResultState}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExposureResultTransformer implements Transformer<CaptureResult, ExposureResultState> {

    @Override
    public ExposureResultState transform(CaptureResult input) {
        Integer autoExposure = input.get(CaptureResult.CONTROL_AE_STATE);
        if (autoExposure != null && isExposureValuesConverged(autoExposure)) {
            return ExposureResultState.SUCCESS;
        }
        return ExposureResultState.FAILURE;
    }

    private boolean isExposureValuesConverged(Integer autoExposure) {
        return autoExposure == CaptureResult.CONTROL_AE_STATE_CONVERGED;
    }
}
