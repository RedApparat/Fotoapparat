package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraAccessException;
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
 * A {@link CaptureCallback} which will provide the resulting {@link Stage} after a still picture
 * capturing request has been performed and pause/resume the preview session.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class CaptureCallback extends StageCallback {

    private final Session session;

    CaptureCallback(Session session) {
        this.session = session;
    }

    @Override
    public void onCaptureStarted(@NonNull CameraCaptureSession session,
                                 @NonNull CaptureRequest request,
                                 long timestamp,
                                 long frameNumber) {
        super.onCaptureStarted(session, request, timestamp, frameNumber);
        stopPreviewSession();
    }

    @Override
    public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                   @NonNull CaptureRequest request,
                                   @NonNull TotalCaptureResult result) {
        super.onCaptureCompleted(session, request, result);
        if (this.session instanceof PreviewSession) {
            ((PreviewSession) this.session).startPreview();
        }
    }

    @Override
    Stage processResult(CaptureResult result) {
        return Stage.CAPTURE_COMPLETED;
    }

    private void stopPreviewSession() {
        try {
            this.session.getCaptureSession().stopRepeating();
        } catch (CameraAccessException e) {
            // Do nothing
        }
    }

}
