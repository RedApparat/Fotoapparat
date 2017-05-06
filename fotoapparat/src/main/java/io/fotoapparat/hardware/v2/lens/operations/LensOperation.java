package io.fotoapparat.hardware.v2.lens.operations;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import java.util.concurrent.Callable;

import io.fotoapparat.result.transformer.Transformer;

/**
 * A generic lens operation which can be executed.
 *
 * @param <T> the type of the expected result.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LensOperation<T> implements Callable<T> {

    private final WrappedCaptureCallback<T> wrappedCaptureCallback;

    /**
     * Creates a new lens operation.
     *
     * @param request        The desired request to the camera.
     * @param handler        The handler to run the callback from the camera.
     * @param transformer    The transformer which will convert the {@link CaptureResult} into a
     *                       desired result.
     * @param captureSession The currently open capture session.
     * @param <T>            The type of the desired result.
     * @return The result of this lens operation.
     */
    public static <T> LensOperation<T> from(CaptureRequest request,
                                            Handler handler,
                                            Transformer<CaptureResult, T> transformer,
                                            CameraCaptureSession captureSession) {

        WrappedCaptureCallback<T> wrappedCaptureCallback = WrappedCaptureCallback.newInstance(
                captureSession,
                request,
                handler,
                transformer
        );

        return new LensOperation<>(wrappedCaptureCallback);
    }

    private LensOperation(WrappedCaptureCallback<T> wrappedCaptureCallback) {
        this.wrappedCaptureCallback = wrappedCaptureCallback;
    }

    @Override
    public T call() {
        return wrappedCaptureCallback.call();
    }

}
