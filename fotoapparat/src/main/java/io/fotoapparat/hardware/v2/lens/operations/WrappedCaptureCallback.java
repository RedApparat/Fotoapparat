package io.fotoapparat.hardware.v2.lens.operations;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.result.transformer.Transformer;

/**
 * Process a {@link CaptureResult} and when finishes can return the processed result.
 *
 * @param <R> the type of the expected result.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class WrappedCaptureCallback<R> extends CameraCaptureSession.CaptureCallback implements Callable<R> {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private final CameraCaptureSession captureSession;
    private final CaptureRequest request;
    private final Handler handler;
    private final Transformer<CaptureResult, R> transformer;

    private R stage;

    private WrappedCaptureCallback(CameraCaptureSession captureSession,
                                   CaptureRequest request,
                                   Handler handler,
                                   Transformer<CaptureResult, R> transformer) {
        this.captureSession = captureSession;
        this.request = request;
        this.handler = handler;
        this.transformer = transformer;
    }

    /**
     * @param captureSession The currently open camera session.
     * @param request        The capture request.
     * @param handler        The handler to get the result from the camera.
     * @param transformer    The transformer to convert the resulting state.
     * @param <R>            The type of the expected result.
     * @return A new instance of this object.
     */
    static <R> WrappedCaptureCallback<R> newInstance(CameraCaptureSession captureSession,
                                                     CaptureRequest request,
                                                     Handler handler,
                                                     Transformer<CaptureResult, R> transformer) {
        return new WrappedCaptureCallback<>(
                captureSession,
                request,
                handler,
                transformer
        );
    }

    @Override
    public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                   @NonNull CaptureRequest request,
                                   @NonNull TotalCaptureResult result) {
        super.onCaptureCompleted(session, request, result);
        this.stage = transformer.transform(result);
        countDownLatch.countDown();
    }

    /**
     * @return The result subsequent to the process.
     */
    private R getResult() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            // do nothing
        }
        return stage;
    }

    @Override
    public R call() {
        try {
            captureSession
                    .capture(
                            request,
                            this,
                            handler
                    );

            return getResult();
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }
}
