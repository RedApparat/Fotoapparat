package io.fotoapparat.hardware.v2.connection;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.operators.ConnectionOperator;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.selection.CameraSelector;
import io.fotoapparat.parameter.LensPosition;

/**
 * A wrapper around {@link CameraDevice.StateCallback} to open and close a camera.
 */
@SuppressWarnings("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraConnection implements ConnectionOperator {

    private final CameraSelector cameraSelector;
    private final CameraManager manager;
    private final CameraThread cameraThread;

    private Characteristics characteristics;
    private CameraDevice camera;
    private Listener listener;

    public CameraConnection(CameraSelector cameraSelector,
                            CameraManager manager,
                            CameraThread cameraThread) {
        this.cameraSelector = cameraSelector;
        this.manager = manager;
        this.cameraThread = cameraThread;
    }

    @Override
    public void open(LensPosition lensPosition) {
        String cameraId = cameraSelector.findCameraId(lensPosition);

        characteristics = new GetCharacteristicsTask(
                manager
        ).execute(cameraId);

        camera = new OpenCameraTask(
                manager,
                cameraThread
        ).execute(cameraId);
    }

    @Override
    public void close() {
        if (camera != null) {
            camera.close();
        }
        if (listener != null) {
            listener.onConnectionClosed();
        }
    }

    /**
     * @return characteristics of currently opened camera.
     * @throws IllegalStateException if camera is not opened yet.
     */
    public Characteristics getCharacteristics() {
        if (characteristics == null) {
            throw new IllegalStateException(
                    "Camera was not opened yet. Characteristics are not available.");
        }

        return characteristics;
    }

    /**
     * Returns the open {@link CameraDevice}.
     *
     * @return The requested {@link CameraDevice} to open
     */
    public CameraDevice getCamera() {
        if (camera == null) {
            throw new IllegalStateException(
                    "Camera was not opened yet. Camera is not available.");
        }

        return camera;
    }

    /**
     * Sets the listener to be notified when the connection closes.
     *
     * @param listener The listener to receive the events.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Notifies the connectivity of the camera.
     */
    public interface Listener {

        /**
         * Called when the connection to the camera has been closed.
         */
        void onConnectionClosed();
    }
}
