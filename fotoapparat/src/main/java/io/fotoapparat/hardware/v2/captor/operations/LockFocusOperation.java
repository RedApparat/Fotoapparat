package io.fotoapparat.hardware.v2.captor.operations;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.v2.captor.operations.transformer.FocusResultTransformer;
import io.fotoapparat.lens.FocusResultState;

/**
 * An operation to lock the lens focus.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class LockFocusOperation extends LensOperation<FocusResultState> {

    LockFocusOperation(CameraCaptureSession captureSession,
                       CaptureRequest request,
                       Handler handler) {
        super(CaptureCallback.newInstance(
                captureSession,
                request,
                handler,
                new FocusResultTransformer()));
    }

}
