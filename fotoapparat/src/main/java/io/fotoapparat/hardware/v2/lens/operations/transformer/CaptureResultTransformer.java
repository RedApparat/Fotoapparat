package io.fotoapparat.hardware.v2.lens.operations.transformer;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.lens.CaptureResultState;
import io.fotoapparat.lens.ExposureResultState;
import io.fotoapparat.result.transformer.Transformer;

/**
 * Transforms a {@link CaptureResult} into a {@link ExposureResultState}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CaptureResultTransformer implements Transformer<CaptureResult, CaptureResultState> {

    @Override
    public CaptureResultState transform(CaptureResult input) {
        return CaptureResultState.SUCCESS;
    }

}
