package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraCaptureSession;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.v2.connection.CameraConnection;

/**
 * Manages a {@link android.hardware.camera2.CameraCaptureSession} of a {@link
 * io.fotoapparat.hardware.v2.Camera2}.
 */
@SuppressWarnings("NewApi")
public class SessionManager implements PreviewOperator, CameraConnection.Listener {

    private final SessionProvider sessionProvider;
    private PreviewSession session;

    public SessionManager(CameraConnection connection,
                          SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        connection.setListener(this);
    }

    @Override
    public void startPreview() {
        try {
            session = sessionProvider.getPreviewSession();
            session.startPreview();
        } catch (IllegalStateException e) {
            throw new CameraException(e.getMessage());
        }
    }

    @Override
    public void stopPreview() {
        session.stopPreview();
    }

    @Override
    public void onConnectionClosed() {
        if (session != null) {
            session.close();
        }
    }

    /**
     * @return the currently opened capture session of the camera
     */
    public CameraCaptureSession getCaptureSession() {
        if (session == null) {
            throw new IllegalStateException("Preview has not been yet started.");
        }
        return session.getCaptureSession();
    }

}
