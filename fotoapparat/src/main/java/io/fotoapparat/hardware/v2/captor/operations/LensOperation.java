package io.fotoapparat.hardware.v2.captor.operations;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.concurrent.Callable;

/**
 * A generic lens operation which can be executed.
 *
 * @param <T> the type of the expected result.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LensOperation<T> implements Callable<T> {

    private final CaptureCallback<T> captureCallback;

    LensOperation(CaptureCallback<T> captureCallback) {
        this.captureCallback = captureCallback;
    }

    @Override
    public T call() {
        return captureCallback.call();
    }

}
