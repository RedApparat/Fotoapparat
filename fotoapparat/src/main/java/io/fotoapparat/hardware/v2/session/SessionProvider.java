package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;

import java.util.Arrays;
import java.util.List;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.readers.StillSurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;

/**
 * Provides {@link PreviewSession}s for the currently given display surface.
 */
@SuppressWarnings("NewApi")
public class SessionProvider implements TextureManager.Listener {

    private PreviewSession previewSession;
    private StillSurfaceReader surfaceReader;
    private CameraConnection connection;
    private CaptureRequestFactory captureRequestFactory;
    private CameraThread cameraThread;

    public SessionProvider(StillSurfaceReader surfaceReader,
                           CameraConnection connection,
                           CaptureRequestFactory captureRequestFactory,
                           TextureManager textureManager, CameraThread cameraThread) {
        this.surfaceReader = surfaceReader;
        this.connection = connection;
        this.captureRequestFactory = captureRequestFactory;
        this.cameraThread = cameraThread;
        textureManager.setListener(this);
    }

    @Override
    public void onSurfaceAvailable(Surface surface) {
        CameraDevice camera = connection.getCamera();

        Surface captureSurface = surfaceReader.getSurface();
        List<Surface> surfaces = Arrays.asList(surface, captureSurface);

        try {
            CaptureRequest previewRequest = captureRequestFactory.createPreviewRequest();

            previewSession = new PreviewSession(
                    camera,
                    previewRequest,
                    surfaces,
                    cameraThread
            );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }

    /**
     * @return The {@link PreviewSession} for the given display surface.
     */
    public PreviewSession getPreviewSession() {
        if (previewSession == null) {
            throw new IllegalStateException("Target display surface has not been set.");
        }
        return previewSession;
    }

}
